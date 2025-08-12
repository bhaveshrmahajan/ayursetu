import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Button, Badge, Spinner } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { consultationAPI, doctorAPI, pharmacyAPI } from '../services/api';
import { toast } from 'react-toastify';
import { 
  FaUser, 
  FaCalendarAlt, 
  FaPills, 
  FaUserMd, 
  FaClock, 
  FaCheckCircle, 
  FaTimesCircle,
  FaArrowRight,
  FaPhone,
  FaEnvelope,
  FaMapMarkerAlt
} from 'react-icons/fa';

const Dashboard = () => {
  const { user } = useAuth();
  const [consultations, setConsultations] = useState([]);
  const [doctors, setDoctors] = useState([]);
  const [medicines, setMedicines] = useState([]);
  const [loading, setLoading] = useState(true);
  const [stats, setStats] = useState({
    totalConsultations: 0,
    upcomingConsultations: 0,
    completedConsultations: 0,
    totalDoctors: 0,
    totalMedicines: 0
  });

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      // Fetch consultations for the user
      const consultationsResponse = await consultationAPI.getConsultationsByUserId(user.id);
      const consultationsData = consultationsResponse.data;
      setConsultations(consultationsData);

      // Fetch available doctors
      const doctorsResponse = await doctorAPI.getAvailableDoctors();
      const doctorsData = doctorsResponse.data.slice(0, 3); // Show only 3 doctors
      setDoctors(doctorsData);

      // Fetch available medicines
      const medicinesResponse = await pharmacyAPI.getAvailableMedicines();
      const medicinesData = medicinesResponse.data.slice(0, 3); // Show only 3 medicines
      setMedicines(medicinesData);

      // Calculate stats
      const upcoming = consultationsData.filter(c => c.status === 'SCHEDULED').length;
      const completed = consultationsData.filter(c => c.status === 'COMPLETED').length;

      setStats({
        totalConsultations: consultationsData.length,
        upcomingConsultations: upcoming,
        completedConsultations: completed,
        totalDoctors: doctorsData.length,
        totalMedicines: medicinesData.length
      });

    } catch (error) {
      toast.error('Failed to load dashboard data');
      console.error('Error fetching dashboard data:', error);
    } finally {
      setLoading(false);
    }
  };

  const getStatusBadge = (status) => {
    const statusConfig = {
      'SCHEDULED': { variant: 'primary', text: 'Scheduled' },
      'COMPLETED': { variant: 'success', text: 'Completed' },
      'CANCELLED': { variant: 'danger', text: 'Cancelled' },
      'IN_PROGRESS': { variant: 'warning', text: 'In Progress' }
    };
    
    const config = statusConfig[status] || { variant: 'secondary', text: status };
    return <Badge bg={config.variant}>{config.text}</Badge>;
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
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
      {/* Welcome Section */}
      <Row className="mb-4">
        <Col>
          <h1 className="section-title">Welcome back, {user?.name || 'User'}!</h1>
          <p className="text-muted">Here's what's happening with your health journey</p>
        </Col>
      </Row>

      {/* Stats Cards */}
      <Row className="mb-4">
        <Col md={3} className="mb-3">
          <Card className="border-0 shadow-sm text-center">
            <Card.Body>
              <div className="bg-primary rounded-circle d-inline-flex align-items-center justify-content-center mb-3" 
                   style={{ width: '50px', height: '50px' }}>
                <FaCalendarAlt className="text-white" />
              </div>
              <h3 className="mb-1">{stats.totalConsultations}</h3>
              <p className="text-muted mb-0">Total Consultations</p>
            </Card.Body>
          </Card>
        </Col>
        <Col md={3} className="mb-3">
          <Card className="border-0 shadow-sm text-center">
            <Card.Body>
              <div className="bg-warning rounded-circle d-inline-flex align-items-center justify-content-center mb-3" 
                   style={{ width: '50px', height: '50px' }}>
                <FaClock className="text-white" />
              </div>
              <h3 className="mb-1">{stats.upcomingConsultations}</h3>
              <p className="text-muted mb-0">Upcoming</p>
            </Card.Body>
          </Card>
        </Col>
        <Col md={3} className="mb-3">
          <Card className="border-0 shadow-sm text-center">
            <Card.Body>
              <div className="bg-success rounded-circle d-inline-flex align-items-center justify-content-center mb-3" 
                   style={{ width: '50px', height: '50px' }}>
                <FaCheckCircle className="text-white" />
              </div>
              <h3 className="mb-1">{stats.completedConsultations}</h3>
              <p className="text-muted mb-0">Completed</p>
            </Card.Body>
          </Card>
        </Col>
        <Col md={3} className="mb-3">
          <Card className="border-0 shadow-sm text-center">
            <Card.Body>
              <div className="bg-info rounded-circle d-inline-flex align-items-center justify-content-center mb-3" 
                   style={{ width: '50px', height: '50px' }}>
                <FaUserMd className="text-white" />
              </div>
              <h3 className="mb-1">{stats.totalDoctors}</h3>
              <p className="text-muted mb-0">Available Doctors</p>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {/* Quick Actions */}
      <Row className="mb-4">
        <Col>
          <Card className="border-0 shadow-sm">
            <Card.Body>
              <h5 className="mb-3">Quick Actions</h5>
              <Row>
                <Col md={3} className="mb-3">
                  <Button as={Link} to="/doctors" variant="primary" className="w-100">
                    <FaUserMd className="me-2" />
                    Find Doctors
                  </Button>
                </Col>
                <Col md={3} className="mb-3">
                  <Button as={Link} to="/consultation" variant="success" className="w-100">
                    <FaCalendarAlt className="me-2" />
                    Book Consultation
                  </Button>
                </Col>
                <Col md={3} className="mb-3">
                  <Button as={Link} to="/pharmacy" variant="warning" className="w-100">
                    <FaPills className="me-2" />
                    Order Medicines
                  </Button>
                </Col>
                <Col md={3} className="mb-3">
                  <Button as={Link} to="/profile" variant="outline-primary" className="w-100">
                    <FaUser className="me-2" />
                    View Profile
                  </Button>
                </Col>
              </Row>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row>
        {/* Recent Consultations */}
        <Col lg={8} className="mb-4">
          <Card className="border-0 shadow-sm">
            <Card.Header className="bg-white border-0">
              <div className="d-flex justify-content-between align-items-center">
                <h5 className="mb-0">Recent Consultations</h5>
                <Button as={Link} to="/consultation" variant="link" className="text-primary p-0">
                  View All <FaArrowRight className="ms-1" />
                </Button>
              </div>
            </Card.Header>
            <Card.Body>
              {consultations.length === 0 ? (
                <div className="text-center py-4">
                  <FaCalendarAlt className="text-muted mb-3" style={{ fontSize: '3rem' }} />
                  <h6 className="text-muted">No consultations yet</h6>
                  <p className="text-muted">Book your first consultation to get started</p>
                  <Button as={Link} to="/consultation" variant="primary">
                    Book Consultation
                  </Button>
                </div>
              ) : (
                <div>
                  {consultations.slice(0, 5).map((consultation) => (
                    <div key={consultation.id} className="d-flex justify-content-between align-items-center py-3 border-bottom">
                      <div>
                        <h6 className="mb-1">{consultation.doctorName || 'Dr. Ayurvedic Specialist'}</h6>
                        <p className="text-muted mb-1">{consultation.specialization || 'General Ayurveda'}</p>
                        <small className="text-muted">{formatDate(consultation.consultationDate)}</small>
                      </div>
                      <div className="text-end">
                        {getStatusBadge(consultation.status)}
                        <div className="mt-2">
                          <Button size="sm" variant="outline-primary">
                            View Details
                          </Button>
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </Card.Body>
          </Card>
        </Col>

        {/* Available Doctors */}
        <Col lg={4} className="mb-4">
          <Card className="border-0 shadow-sm">
            <Card.Header className="bg-white border-0">
              <div className="d-flex justify-content-between align-items-center">
                <h5 className="mb-0">Available Doctors</h5>
                <Button as={Link} to="/doctors" variant="link" className="text-primary p-0">
                  View All <FaArrowRight className="ms-1" />
                </Button>
              </div>
            </Card.Header>
            <Card.Body>
              {doctors.length === 0 ? (
                <div className="text-center py-4">
                  <FaUserMd className="text-muted mb-3" style={{ fontSize: '3rem' }} />
                  <h6 className="text-muted">No doctors available</h6>
                </div>
              ) : (
                <div>
                  {doctors.map((doctor) => (
                    <div key={doctor.id} className="d-flex align-items-center py-3 border-bottom">
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
                      <Button size="sm" variant="outline-primary">
                        Book
                      </Button>
                    </div>
                  ))}
                </div>
              )}
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {/* User Profile Summary */}
      <Row>
        <Col>
          <Card className="border-0 shadow-sm">
            <Card.Header className="bg-white border-0">
              <h5 className="mb-0">Your Profile</h5>
            </Card.Header>
            <Card.Body>
              <Row>
                <Col md={6}>
                  <div className="d-flex align-items-center mb-3">
                    <FaUser className="text-primary me-3" />
                    <div>
                      <h6 className="mb-1">Name</h6>
                      <p className="text-muted mb-0">{user?.name || 'Not provided'}</p>
                    </div>
                  </div>
                  <div className="d-flex align-items-center mb-3">
                    <FaEnvelope className="text-primary me-3" />
                    <div>
                      <h6 className="mb-1">Email</h6>
                      <p className="text-muted mb-0">{user?.email || 'Not provided'}</p>
                    </div>
                  </div>
                </Col>
                <Col md={6}>
                  <div className="d-flex align-items-center mb-3">
                    <FaPhone className="text-primary me-3" />
                    <div>
                      <h6 className="mb-1">Phone</h6>
                      <p className="text-muted mb-0">{user?.phone || 'Not provided'}</p>
                    </div>
                  </div>
                  <div className="d-flex align-items-center mb-3">
                    <FaMapMarkerAlt className="text-primary me-3" />
                    <div>
                      <h6 className="mb-1">Location</h6>
                      <p className="text-muted mb-0">{user?.address || 'Not provided'}</p>
                    </div>
                  </div>
                </Col>
              </Row>
              <div className="text-center mt-3">
                <Button as={Link} to="/profile" variant="outline-primary">
                  Edit Profile
                </Button>
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default Dashboard;





