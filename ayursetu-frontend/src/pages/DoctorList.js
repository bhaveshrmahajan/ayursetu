import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Button, Form, InputGroup, Badge, Spinner } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { doctorAPI } from '../services/api';
import { toast } from 'react-toastify';
import { FaSearch, FaUserMd, FaMapMarkerAlt, FaStar, FaPhone, FaEnvelope } from 'react-icons/fa';

const DoctorList = () => {
  const [doctors, setDoctors] = useState([]);
  const [filteredDoctors, setFilteredDoctors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedSpecialization, setSelectedSpecialization] = useState('');
  const [selectedCity, setSelectedCity] = useState('');
  const [specializations, setSpecializations] = useState([]);
  const [cities, setCities] = useState([]);

  useEffect(() => {
    fetchDoctors();
    fetchSpecializations();
    fetchCities();
  }, []);

  useEffect(() => {
    filterDoctors();
  }, [doctors, searchTerm, selectedSpecialization, selectedCity]);

  const fetchDoctors = async () => {
    try {
      const response = await doctorAPI.getAllDoctors();
      setDoctors(response.data);
      setFilteredDoctors(response.data);
    } catch (error) {
      toast.error('Failed to fetch doctors');
      console.error('Error fetching doctors:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchSpecializations = async () => {
    try {
      const response = await doctorAPI.getAllSpecializations();
      setSpecializations(response.data);
    } catch (error) {
      console.error('Error fetching specializations:', error);
    }
  };

  const fetchCities = async () => {
    try {
      const response = await doctorAPI.getAllCities();
      setCities(response.data);
    } catch (error) {
      console.error('Error fetching cities:', error);
    }
  };

  const filterDoctors = () => {
    let filtered = doctors;

    // Filter by search term
    if (searchTerm) {
      filtered = filtered.filter(doctor =>
        doctor.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        doctor.specialization?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        doctor.city?.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }

    // Filter by specialization
    if (selectedSpecialization) {
      filtered = filtered.filter(doctor =>
        doctor.specialization === selectedSpecialization
      );
    }

    // Filter by city
    if (selectedCity) {
      filtered = filtered.filter(doctor =>
        doctor.city === selectedCity
      );
    }

    setFilteredDoctors(filtered);
  };

  const clearFilters = () => {
    setSearchTerm('');
    setSelectedSpecialization('');
    setSelectedCity('');
  };

  const getRatingStars = (rating) => {
    return Array.from({ length: 5 }, (_, i) => (
      <FaStar
        key={i}
        className={i < rating ? 'text-warning' : 'text-muted'}
      />
    ));
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
          <h1 className="section-title">Find Ayurvedic Doctors</h1>
          <p className="text-muted">Connect with qualified Ayurvedic practitioners in your area</p>
        </Col>
      </Row>

      {/* Search and Filters */}
      <Row className="mb-4">
        <Col lg={12}>
          <Card className="border-0 shadow-sm">
            <Card.Body>
              <Row>
                <Col md={4} className="mb-3">
                  <InputGroup>
                    <InputGroup.Text>
                      <FaSearch />
                    </InputGroup.Text>
                    <Form.Control
                      type="text"
                      placeholder="Search doctors, specializations, or cities..."
                      value={searchTerm}
                      onChange={(e) => setSearchTerm(e.target.value)}
                    />
                  </InputGroup>
                </Col>
                <Col md={3} className="mb-3">
                  <Form.Select
                    value={selectedSpecialization}
                    onChange={(e) => setSelectedSpecialization(e.target.value)}
                  >
                    <option value="">All Specializations</option>
                    {specializations.map((spec, index) => (
                      <option key={index} value={spec}>{spec}</option>
                    ))}
                  </Form.Select>
                </Col>
                <Col md={3} className="mb-3">
                  <Form.Select
                    value={selectedCity}
                    onChange={(e) => setSelectedCity(e.target.value)}
                  >
                    <option value="">All Cities</option>
                    {cities.map((city, index) => (
                      <option key={index} value={city}>{city}</option>
                    ))}
                  </Form.Select>
                </Col>
                <Col md={2} className="mb-3">
                  <Button
                    variant="outline-secondary"
                    onClick={clearFilters}
                    className="w-100"
                  >
                    Clear
                  </Button>
                </Col>
              </Row>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {/* Results Count */}
      <Row className="mb-4">
        <Col>
          <p className="text-muted">
            Found {filteredDoctors.length} doctor{filteredDoctors.length !== 1 ? 's' : ''}
            {searchTerm || selectedSpecialization || selectedCity ? ' matching your criteria' : ''}
          </p>
        </Col>
      </Row>

      {/* Doctors Grid */}
      <Row>
        {filteredDoctors.length === 0 ? (
          <Col className="text-center py-5">
            <FaUserMd className="text-muted mb-3" style={{ fontSize: '4rem' }} />
            <h4 className="text-muted">No doctors found</h4>
            <p className="text-muted">Try adjusting your search criteria</p>
          </Col>
        ) : (
          filteredDoctors.map((doctor) => (
            <Col key={doctor.id} lg={4} md={6} className="mb-4">
              <Card className="h-100 border-0 shadow-sm">
                <Card.Body>
                  <div className="d-flex align-items-start mb-3">
                    <div className="bg-primary rounded-circle me-3 d-flex align-items-center justify-content-center" 
                         style={{ width: '60px', height: '60px' }}>
                      <FaUserMd className="text-white" />
                    </div>
                    <div className="flex-grow-1">
                      <h5 className="mb-1">{doctor.name}</h5>
                      <p className="text-muted mb-1">{doctor.specialization}</p>
                      <div className="d-flex align-items-center mb-2">
                        {getRatingStars(doctor.rating || 0)}
                        <span className="ms-2 text-muted">({doctor.rating || 0})</span>
                      </div>
                    </div>
                  </div>

                  <div className="mb-3">
                    <div className="d-flex align-items-center mb-2">
                      <FaMapMarkerAlt className="text-muted me-2" />
                      <span className="text-muted">{doctor.city}</span>
                    </div>
                    <div className="d-flex align-items-center mb-2">
                      <FaPhone className="text-muted me-2" />
                      <span className="text-muted">{doctor.phone}</span>
                    </div>
                    <div className="d-flex align-items-center">
                      <FaEnvelope className="text-muted me-2" />
                      <span className="text-muted">{doctor.email}</span>
                    </div>
                  </div>

                  <div className="mb-3">
                    <Badge bg="success" className="me-2">
                      ₹{doctor.consultationFee}/consultation
                    </Badge>
                    {doctor.isAvailable && (
                      <Badge bg="primary">Available</Badge>
                    )}
                    {doctor.isVerified && (
                      <Badge bg="warning" className="ms-2">Verified</Badge>
                    )}
                  </div>

                  <p className="text-muted small mb-3">
                    {doctor.description || 'Experienced Ayurvedic practitioner with expertise in holistic healthcare.'}
                  </p>

                  <div className="d-flex gap-2">
                    <Button
                      as={Link}
                      to={`/doctors/${doctor.id}`}
                      variant="primary"
                      size="sm"
                      className="flex-grow-1"
                    >
                      View Profile
                    </Button>
                    <Button
                      as={Link}
                      to={`/consultation?doctorId=${doctor.id}`}
                      variant="outline-primary"
                      size="sm"
                    >
                      Book Consultation
                    </Button>
                  </div>
                </Card.Body>
              </Card>
            </Col>
          ))
        )}
      </Row>
    </Container>
  );
};

export default DoctorList;





