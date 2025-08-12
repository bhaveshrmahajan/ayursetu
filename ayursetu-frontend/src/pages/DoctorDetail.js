import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Button, Badge, Spinner, Alert } from 'react-bootstrap';
import { useParams, Link } from 'react-router-dom';
import { doctorAPI } from '../services/api';
import { toast } from 'react-toastify';
import { 
  FaUserMd, 
  FaMapMarkerAlt, 
  FaPhone, 
  FaEnvelope, 
  FaStar, 
  FaCalendarAlt,
  FaClock,
  FaGraduationCap,
  FaAward,
  FaCheckCircle,
  FaTimesCircle
} from 'react-icons/fa';

const DoctorDetail = () => {
  const { id } = useParams();
  const [doctor, setDoctor] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchDoctorDetails();
  }, [id]);

  const fetchDoctorDetails = async () => {
    try {
      const response = await doctorAPI.getDoctorById(id);
      setDoctor(response.data);
    } catch (error) {
      setError('Failed to fetch doctor details');
      toast.error('Failed to load doctor information');
      console.error('Error fetching doctor details:', error);
    } finally {
      setLoading(false);
    }
  };

  const getRatingStars = (rating) => {
    return Array.from({ length: 5 }, (_, i) => (
      <FaStar
        key={i}
        className={i < rating ? 'text-warning' : 'text-muted'}
      />
    ));
  };

  const getAvailabilityStatus = (isAvailable) => {
    return isAvailable ? (
      <Badge bg="success" className="me-2">
        <FaCheckCircle className="me-1" />
        Available
      </Badge>
    ) : (
      <Badge bg="danger" className="me-2">
        <FaTimesCircle className="me-1" />
        Not Available
      </Badge>
    );
  };

  const getVerificationStatus = (isVerified) => {
    return isVerified ? (
      <Badge bg="warning" className="me-2">
        <FaAward className="me-1" />
        Verified
      </Badge>
    ) : (
      <Badge bg="secondary" className="me-2">
        Pending Verification
      </Badge>
    );
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

  if (error || !doctor) {
    return (
      <Container className="py-5">
        <Alert variant="danger">
          <h4>Error</h4>
          <p>{error || 'Doctor not found'}</p>
          <Button as={Link} to="/doctors" variant="outline-danger">
            Back to Doctors
          </Button>
        </Alert>
      </Container>
    );
  }

  return (
    <Container className="py-5">
      <Row className="mb-4">
        <Col>
          <Button as={Link} to="/doctors" variant="outline-primary" className="mb-3">
            ← Back to Doctors
          </Button>
        </Col>
      </Row>

      <Row>
        <Col lg={8}>
          {/* Doctor Profile Card */}
          <Card className="border-0 shadow-sm mb-4">
            <Card.Body>
              <Row>
                <Col md={4} className="text-center mb-4">
                  <div className="bg-primary rounded-circle d-inline-flex align-items-center justify-content-center mb-3" 
                       style={{ width: '120px', height: '120px' }}>
                    <FaUserMd className="text-white" style={{ fontSize: '3rem' }} />
                  </div>
                  <h4 className="mb-2">{doctor.name}</h4>
                  <p className="text-muted mb-2">{doctor.specialization}</p>
                  <div className="d-flex justify-content-center mb-3">
                    {getRatingStars(doctor.rating || 0)}
                    <span className="ms-2 text-muted">({doctor.rating || 0})</span>
                  </div>
                  <div className="d-flex justify-content-center flex-wrap">
                    {getAvailabilityStatus(doctor.isAvailable)}
                    {getVerificationStatus(doctor.isVerified)}
                  </div>
                </Col>
                
                <Col md={8}>
                  <div className="mb-4">
                    <h5>About Dr. {doctor.name}</h5>
                    <p className="text-muted">
                      {doctor.description || 'Experienced Ayurvedic practitioner with expertise in holistic healthcare and traditional healing methods.'}
                    </p>
                  </div>

                  <div className="row mb-4">
                    <Col md={6}>
                      <div className="d-flex align-items-center mb-3">
                        <FaMapMarkerAlt className="text-primary me-3" />
                        <div>
                          <h6 className="mb-1">Location</h6>
                          <p className="text-muted mb-0">{doctor.city}</p>
                        </div>
                      </div>
                    </Col>
                    <Col md={6}>
                      <div className="d-flex align-items-center mb-3">
                        <FaPhone className="text-primary me-3" />
                        <div>
                          <h6 className="mb-1">Phone</h6>
                          <p className="text-muted mb-0">{doctor.phone}</p>
                        </div>
                      </div>
                    </Col>
                    <Col md={6}>
                      <div className="d-flex align-items-center mb-3">
                        <FaEnvelope className="text-primary me-3" />
                        <div>
                          <h6 className="mb-1">Email</h6>
                          <p className="text-muted mb-0">{doctor.email}</p>
                        </div>
                      </div>
                    </Col>
                    <Col md={6}>
                      <div className="d-flex align-items-center mb-3">
                        <FaGraduationCap className="text-primary me-3" />
                        <div>
                          <h6 className="mb-1">Experience</h6>
                          <p className="text-muted mb-0">{doctor.experience || '10+'} years</p>
                        </div>
                      </div>
                    </Col>
                  </div>

                  <div className="d-flex gap-3">
                    <Button 
                      as={Link} 
                      to={`/consultation?doctorId=${doctor.id}`}
                      variant="primary" 
                      size="lg"
                      disabled={!doctor.isAvailable}
                    >
                      <FaCalendarAlt className="me-2" />
                      Book Consultation
                    </Button>
                    <Button variant="outline-primary" size="lg">
                      <FaPhone className="me-2" />
                      Call Now
                    </Button>
                  </div>
                </Col>
              </Row>
            </Card.Body>
          </Card>

          {/* Consultation Fee */}
          <Card className="border-0 shadow-sm mb-4">
            <Card.Header className="bg-white border-0">
              <h5 className="mb-0">Consultation Fee</h5>
            </Card.Header>
            <Card.Body>
              <div className="d-flex justify-content-between align-items-center">
                <div>
                  <h4 className="text-primary mb-1">₹{doctor.consultationFee}</h4>
                  <p className="text-muted mb-0">Per consultation (30-45 minutes)</p>
                </div>
                <div className="text-end">
                  <Badge bg="success" className="mb-2">Payment after consultation</Badge>
                  <p className="text-muted small mb-0">No advance payment required</p>
                </div>
              </div>
            </Card.Body>
          </Card>

          {/* Specializations */}
          <Card className="border-0 shadow-sm mb-4">
            <Card.Header className="bg-white border-0">
              <h5 className="mb-0">Specializations</h5>
            </Card.Header>
            <Card.Body>
              <div className="row">
                <Col md={6}>
                  <ul className="list-unstyled">
                    <li className="mb-2">
                      <FaCheckCircle className="text-success me-2" />
                      {doctor.specialization}
                    </li>
                    <li className="mb-2">
                      <FaCheckCircle className="text-success me-2" />
                      General Ayurveda
                    </li>
                    <li className="mb-2">
                      <FaCheckCircle className="text-success me-2" />
                      Panchakarma Therapy
                    </li>
                    <li className="mb-2">
                      <FaCheckCircle className="text-success me-2" />
                      Herbal Medicine
                    </li>
                  </ul>
                </Col>
                <Col md={6}>
                  <ul className="list-unstyled">
                    <li className="mb-2">
                      <FaCheckCircle className="text-success me-2" />
                      Lifestyle Counseling
                    </li>
                    <li className="mb-2">
                      <FaCheckCircle className="text-success me-2" />
                      Diet & Nutrition
                    </li>
                    <li className="mb-2">
                      <FaCheckCircle className="text-success me-2" />
                      Stress Management
                    </li>
                    <li className="mb-2">
                      <FaCheckCircle className="text-success me-2" />
                      Preventive Care
                    </li>
                  </ul>
                </Col>
              </div>
            </Card.Body>
          </Card>

          {/* Working Hours */}
          <Card className="border-0 shadow-sm mb-4">
            <Card.Header className="bg-white border-0">
              <h5 className="mb-0">Working Hours</h5>
            </Card.Header>
            <Card.Body>
              <div className="row">
                <Col md={6}>
                  <div className="d-flex justify-content-between mb-2">
                    <span>Monday - Friday</span>
                    <span>9:00 AM - 6:00 PM</span>
                  </div>
                  <div className="d-flex justify-content-between mb-2">
                    <span>Saturday</span>
                    <span>9:00 AM - 2:00 PM</span>
                  </div>
                  <div className="d-flex justify-content-between mb-2">
                    <span>Sunday</span>
                    <span>Closed</span>
                  </div>
                </Col>
                <Col md={6}>
                  <Alert variant="info" className="mb-0">
                    <FaClock className="me-2" />
                    <strong>Note:</strong> Consultation slots are available in 30-minute intervals.
                  </Alert>
                </Col>
              </div>
            </Card.Body>
          </Card>
        </Col>

        <Col lg={4}>
          {/* Quick Actions */}
          <Card className="border-0 shadow-sm mb-4">
            <Card.Header className="bg-white border-0">
              <h5 className="mb-0">Quick Actions</h5>
            </Card.Header>
            <Card.Body>
              <div className="d-grid gap-3">
                <Button 
                  as={Link} 
                  to={`/consultation?doctorId=${doctor.id}`}
                  variant="primary"
                  disabled={!doctor.isAvailable}
                >
                  <FaCalendarAlt className="me-2" />
                  Book Appointment
                </Button>
                <Button variant="outline-primary">
                  <FaPhone className="me-2" />
                  Call Doctor
                </Button>
                <Button variant="outline-secondary">
                  <FaEnvelope className="me-2" />
                  Send Message
                </Button>
              </div>
            </Card.Body>
          </Card>

          {/* Contact Information */}
          <Card className="border-0 shadow-sm mb-4">
            <Card.Header className="bg-white border-0">
              <h5 className="mb-0">Contact Information</h5>
            </Card.Header>
            <Card.Body>
              <div className="mb-3">
                <h6>Phone</h6>
                <p className="text-muted mb-0">{doctor.phone}</p>
              </div>
              <div className="mb-3">
                <h6>Email</h6>
                <p className="text-muted mb-0">{doctor.email}</p>
              </div>
              <div className="mb-3">
                <h6>Location</h6>
                <p className="text-muted mb-0">{doctor.city}</p>
              </div>
            </Card.Body>
          </Card>

          {/* Consultation Info */}
          <Card className="border-0 shadow-sm">
            <Card.Header className="bg-white border-0">
              <h5 className="mb-0">Consultation Info</h5>
            </Card.Header>
            <Card.Body>
              <div className="mb-3">
                <h6>Duration</h6>
                <p className="text-muted mb-0">30-45 minutes</p>
              </div>
              <div className="mb-3">
                <h6>Languages</h6>
                <p className="text-muted mb-0">English, Hindi, Marathi</p>
              </div>
              <div className="mb-3">
                <h6>Consultation Type</h6>
                <p className="text-muted mb-0">In-person & Online</p>
              </div>
              <Alert variant="info" className="mb-0">
                <strong>Tip:</strong> Bring your medical history and current medications for a better consultation.
              </Alert>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default DoctorDetail;





