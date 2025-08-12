import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Form, Button, Alert, Spinner, Badge } from 'react-bootstrap';
import { useSearchParams } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { consultationAPI, doctorAPI } from '../services/api';
import { toast } from 'react-toastify';
import { FaUserMd, FaCalendarAlt, FaClock, FaMapMarkerAlt, FaPhone, FaEnvelope, FaVideo, FaPhoneAlt, FaComments, FaUserFriends } from 'react-icons/fa';

const Consultation = () => {
  const { user } = useAuth();
  const [searchParams] = useSearchParams();
  const [doctors, setDoctors] = useState([]);
  const [selectedDoctor, setSelectedDoctor] = useState(null);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [formData, setFormData] = useState({
    appointmentDateTime: '',
    consultationType: 'VIDEO_CALL',
    symptoms: '',
    notes: ''
  });
  const [errors, setErrors] = useState({});

  useEffect(() => {
    fetchDoctors();
    const doctorId = searchParams.get('doctorId');
    if (doctorId) {
      fetchDoctorById(doctorId);
    }
  }, [searchParams]);

  const fetchDoctors = async () => {
    try {
      const response = await doctorAPI.getAvailableDoctors();
      setDoctors(response.data);
    } catch (error) {
      toast.error('Failed to fetch doctors');
      console.error('Error fetching doctors:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchDoctorById = async (doctorId) => {
    try {
      const response = await doctorAPI.getDoctorById(doctorId);
      setSelectedDoctor(response.data);
    } catch (error) {
      console.error('Error fetching doctor:', error);
    }
  };

  const handleDoctorSelect = (doctor) => {
    setSelectedDoctor(doctor);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
    // Clear error when user starts typing
    if (errors[name]) {
      setErrors(prev => ({
        ...prev,
        [name]: ''
      }));
    }
  };

  const validateForm = () => {
    const newErrors = {};

    if (!selectedDoctor) {
      newErrors.doctor = 'Please select a doctor';
    }

    if (!formData.appointmentDateTime) {
      newErrors.appointmentDateTime = 'Please select appointment date and time';
    } else {
      const selectedDateTime = new Date(formData.appointmentDateTime);
      const now = new Date();
      
      if (selectedDateTime <= now) {
        newErrors.appointmentDateTime = 'Please select a future date and time';
      }
    }

    if (!formData.symptoms.trim()) {
      newErrors.symptoms = 'Please describe your symptoms';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!validateForm()) {
      return;
    }

    setSubmitting(true);
    
    try {
      const consultationData = {
        doctorId: selectedDoctor.id,
        userId: user.id,
        appointmentDateTime: formData.appointmentDateTime,
        type: formData.consultationType,
        symptoms: formData.symptoms,
        notes: formData.notes,
        consultationFee: selectedDoctor.consultationFee,
        status: 'SCHEDULED'
      };

      const response = await consultationAPI.createConsultation(consultationData);
      
      if (response.data) {
        toast.success('Consultation booked successfully!');
        // Reset form
        setFormData({
          appointmentDateTime: '',
          consultationType: 'VIDEO_CALL',
          symptoms: '',
          notes: ''
        });
        setSelectedDoctor(null);
      }
    } catch (error) {
      toast.error('Failed to book consultation');
      console.error('Error booking consultation:', error);
    } finally {
      setSubmitting(false);
    }
  };

  const getConsultationTypeIcon = (type) => {
    switch (type) {
      case 'VIDEO_CALL':
        return <FaVideo />;
      case 'AUDIO_CALL':
        return <FaPhoneAlt />;
      case 'CHAT':
        return <FaComments />;
      case 'IN_PERSON':
        return <FaUserFriends />;
      default:
        return <FaVideo />;
    }
  };

  if (loading) {
    return (
      <Container className="py-5">
        <div className="loading-spinner">
          <Spinner animation="border" role="status" variant="primary">
            <span className="visually-hidden">Loading...</span>
          </Spinner>
        </div>
      </Container>
    );
  }

  return (
    <Container className="py-5">
      <Row className="mb-4">
        <Col>
          <h1 className="section-title">Book Consultation</h1>
          <p className="text-muted">Schedule an appointment with an Ayurvedic specialist</p>
        </Col>
      </Row>

      <Row>
        <Col lg={8}>
          <Card className="border-0 shadow-sm">
            <Card.Header className="bg-white border-0">
              <h5 className="mb-0">Consultation Details</h5>
            </Card.Header>
            <Card.Body>
              <Form onSubmit={handleSubmit}>
                {/* Doctor Selection */}
                <Form.Group className="mb-4">
                  <Form.Label>Select Doctor</Form.Label>
                  {selectedDoctor ? (
                    <Card className="border-primary">
                      <Card.Body>
                        <div className="d-flex align-items-center">
                          <div className="bg-primary rounded-circle me-3 d-flex align-items-center justify-content-center" 
                               style={{ width: '50px', height: '50px' }}>
                            <FaUserMd className="text-white" />
                          </div>
                          <div className="flex-grow-1">
                            <h6 className="mb-1">{selectedDoctor.name}</h6>
                            <p className="text-muted mb-1">{selectedDoctor.specialization}</p>
                            <div className="d-flex align-items-center">
                              <FaMapMarkerAlt className="text-muted me-2" />
                              <span className="text-muted">{selectedDoctor.city}</span>
                            </div>
                          </div>
                          <div className="text-end">
                            <Badge bg="success" className="mb-2">₹{selectedDoctor.consultationFee}</Badge>
                            <Button
                              variant="outline-secondary"
                              size="sm"
                              onClick={() => setSelectedDoctor(null)}
                            >
                              Change
                            </Button>
                          </div>
                        </div>
                      </Card.Body>
                    </Card>
                  ) : (
                    <div>
                      <Form.Control
                        type="text"
                        placeholder="Search doctors..."
                        className="mb-3"
                      />
                      <div className="row">
                        {doctors.map((doctor) => (
                          <Col key={doctor.id} md={6} className="mb-3">
                            <Card 
                              className={`border-0 shadow-sm cursor-pointer ${selectedDoctor?.id === doctor.id ? 'border-primary' : ''}`}
                              onClick={() => handleDoctorSelect(doctor)}
                            >
                              <Card.Body>
                                <div className="d-flex align-items-center">
                                  <div className="bg-primary rounded-circle me-3 d-flex align-items-center justify-content-center" 
                                       style={{ width: '40px', height: '40px' }}>
                                    <FaUserMd className="text-white" />
                                  </div>
                                  <div className="flex-grow-1">
                                    <h6 className="mb-1">{doctor.name}</h6>
                                    <p className="text-muted mb-1">{doctor.specialization}</p>
                                    <small className="text-muted">
                                      <FaMapMarkerAlt className="me-1" />
                                      {doctor.city}
                                    </small>
                                  </div>
                                  <div className="text-end">
                                    <Badge bg="success">₹{doctor.consultationFee}</Badge>
                                  </div>
                                </div>
                              </Card.Body>
                            </Card>
                          </Col>
                        ))}
                      </div>
                    </div>
                  )}
                  {errors.doctor && (
                    <Form.Control.Feedback type="invalid" className="d-block">
                      {errors.doctor}
                    </Form.Control.Feedback>
                  )}
                </Form.Group>

                <Row>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>Appointment Date & Time</Form.Label>
                      <div className="position-relative">
                        <Form.Control
                          type="datetime-local"
                          name="appointmentDateTime"
                          value={formData.appointmentDateTime}
                          onChange={handleChange}
                          isInvalid={!!errors.appointmentDateTime}
                          min={new Date().toISOString().slice(0, 16)}
                        />
                        <FaCalendarAlt className="position-absolute top-50 end-0 translate-middle-y me-3 text-muted" />
                      </div>
                      <Form.Control.Feedback type="invalid">
                        {errors.appointmentDateTime}
                      </Form.Control.Feedback>
                    </Form.Group>
                  </Col>
                  
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>Consultation Type</Form.Label>
                      <div className="position-relative">
                        <Form.Select
                          name="consultationType"
                          value={formData.consultationType}
                          onChange={handleChange}
                        >
                          <option value="VIDEO_CALL">
                            <FaVideo className="me-2" />
                            Video Call
                          </option>
                          <option value="AUDIO_CALL">
                            <FaPhoneAlt className="me-2" />
                            Audio Call
                          </option>
                          <option value="CHAT">
                            <FaComments className="me-2" />
                            Chat
                          </option>
                          <option value="IN_PERSON">
                            <FaUserFriends className="me-2" />
                            In Person
                          </option>
                        </Form.Select>
                        <div className="position-absolute top-50 end-0 translate-middle-y me-3 text-muted">
                          {getConsultationTypeIcon(formData.consultationType)}
                        </div>
                      </div>
                    </Form.Group>
                  </Col>
                </Row>

                <Form.Group className="mb-3">
                  <Form.Label>Describe Your Symptoms</Form.Label>
                  <Form.Control
                    as="textarea"
                    rows={4}
                    name="symptoms"
                    value={formData.symptoms}
                    onChange={handleChange}
                    placeholder="Please describe your symptoms, concerns, or health issues..."
                    isInvalid={!!errors.symptoms}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.symptoms}
                  </Form.Control.Feedback>
                </Form.Group>

                <Form.Group className="mb-4">
                  <Form.Label>Additional Notes (Optional)</Form.Label>
                  <Form.Control
                    as="textarea"
                    rows={3}
                    name="notes"
                    value={formData.notes}
                    onChange={handleChange}
                    placeholder="Any additional information, preferences, or special requests..."
                  />
                </Form.Group>

                <Button
                  type="submit"
                  variant="primary"
                  size="lg"
                  className="w-100"
                  disabled={submitting || !selectedDoctor}
                >
                  {submitting ? 'Booking Consultation...' : 'Book Consultation'}
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Col>

        <Col lg={4}>
          <Card className="border-0 shadow-sm">
            <Card.Header className="bg-white border-0">
              <h5 className="mb-0">Consultation Information</h5>
            </Card.Header>
            <Card.Body>
              <div className="mb-4">
                <h6>What to Expect</h6>
                <ul className="text-muted small">
                  <li>Initial consultation lasts 30-45 minutes</li>
                  <li>Doctor will review your symptoms and medical history</li>
                  <li>Personalized treatment plan will be provided</li>
                  <li>Follow-up appointments may be recommended</li>
                </ul>
              </div>

              <div className="mb-4">
                <h6>Preparation Tips</h6>
                <ul className="text-muted small">
                  <li>Bring any recent medical reports</li>
                  <li>List your current medications</li>
                  <li>Note down your symptoms in detail</li>
                  <li>Be ready to discuss your lifestyle</li>
                </ul>
              </div>

              <div className="mb-4">
                <h6>Consultation Fee</h6>
                <p className="text-muted">
                  {selectedDoctor ? (
                    <>
                      Consultation fee: <strong>₹{selectedDoctor.consultationFee}</strong><br />
                      Payment is collected after the consultation
                    </>
                  ) : (
                    <>
                      Standard consultation fee: <strong>₹500-1500</strong><br />
                      Payment is collected after the consultation
                    </>
                  )}
                </p>
              </div>

              <Alert variant="info">
                <strong>Note:</strong> You can cancel or reschedule your appointment up to 24 hours before the scheduled time.
              </Alert>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default Consultation;





