import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Form, Button, Alert, Spinner } from 'react-bootstrap';
import { useAuth } from '../contexts/AuthContext';
import { userAPI } from '../services/api';
import { toast } from 'react-toastify';
import { FaUser, FaEnvelope, FaPhone, FaMapMarkerAlt, FaEdit, FaSave, FaTimes } from 'react-icons/fa';

const Profile = () => {
  const { user, updateProfile } = useAuth();
  const [loading, setLoading] = useState(false);
  const [editing, setEditing] = useState(false);
  const [userData, setUserData] = useState({
    name: '',
    email: '',
    phone: '',
    age: '',
    gender: '',
    address: ''
  });
  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (user) {
      setUserData({
        name: user.name || '',
        email: user.email || '',
        phone: user.phone || '',
        age: user.age || '',
        gender: user.gender || '',
        address: user.address || ''
      });
    }
  }, [user]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData(prev => ({
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

    if (!userData.name.trim()) {
      newErrors.name = 'Name is required';
    }

    if (!userData.email) {
      newErrors.email = 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(userData.email)) {
      newErrors.email = 'Email is invalid';
    }

    if (!userData.phone) {
      newErrors.phone = 'Phone number is required';
    } else if (!/^\d{10}$/.test(userData.phone.replace(/\D/g, ''))) {
      newErrors.phone = 'Please enter a valid 10-digit phone number';
    }

    if (!userData.age) {
      newErrors.age = 'Age is required';
    } else if (isNaN(userData.age) || userData.age < 1 || userData.age > 120) {
      newErrors.age = 'Please enter a valid age';
    }

    if (!userData.gender) {
      newErrors.gender = 'Please select your gender';
    }

    if (!userData.address.trim()) {
      newErrors.address = 'Address is required';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!validateForm()) {
      return;
    }

    setLoading(true);
    
    try {
      const result = await updateProfile(userData);
      
      if (result.success) {
        toast.success('Profile updated successfully!');
        setEditing(false);
      } else {
        toast.error(result.error || 'Failed to update profile');
      }
    } catch (error) {
      toast.error('An error occurred while updating profile');
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    setEditing(false);
    setErrors({});
    // Reset form to original user data
    setUserData({
      name: user.name || '',
      email: user.email || '',
      phone: user.phone || '',
      age: user.age || '',
      gender: user.gender || '',
      address: user.address || ''
    });
  };

  if (!user) {
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
          <h1 className="section-title">My Profile</h1>
          <p className="text-muted">Manage your personal information and preferences</p>
        </Col>
      </Row>

      <Row>
        <Col lg={8} className="mx-auto">
          <Card className="border-0 shadow-sm">
            <Card.Header className="bg-white border-0">
              <div className="d-flex justify-content-between align-items-center">
                <h5 className="mb-0">Personal Information</h5>
                {!editing ? (
                  <Button
                    variant="outline-primary"
                    size="sm"
                    onClick={() => setEditing(true)}
                  >
                    <FaEdit className="me-1" />
                    Edit Profile
                  </Button>
                ) : (
                  <div className="d-flex gap-2">
                    <Button
                      variant="success"
                      size="sm"
                      type="submit"
                      form="profile-form"
                      disabled={loading}
                    >
                      <FaSave className="me-1" />
                      {loading ? 'Saving...' : 'Save Changes'}
                    </Button>
                    <Button
                      variant="outline-secondary"
                      size="sm"
                      onClick={handleCancel}
                    >
                      <FaTimes className="me-1" />
                      Cancel
                    </Button>
                  </div>
                )}
              </div>
            </Card.Header>
            <Card.Body>
              <Form id="profile-form" onSubmit={handleSubmit}>
                <Row>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>Full Name</Form.Label>
                      <div className="position-relative">
                        <Form.Control
                          type="text"
                          name="name"
                          value={userData.name}
                          onChange={handleChange}
                          disabled={!editing}
                          isInvalid={!!errors.name}
                          className="ps-5"
                        />
                        <FaUser className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" />
                      </div>
                      <Form.Control.Feedback type="invalid">
                        {errors.name}
                      </Form.Control.Feedback>
                    </Form.Group>
                  </Col>
                  
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>Email Address</Form.Label>
                      <div className="position-relative">
                        <Form.Control
                          type="email"
                          name="email"
                          value={userData.email}
                          onChange={handleChange}
                          disabled={!editing}
                          isInvalid={!!errors.email}
                          className="ps-5"
                        />
                        <FaEnvelope className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" />
                      </div>
                      <Form.Control.Feedback type="invalid">
                        {errors.email}
                      </Form.Control.Feedback>
                    </Form.Group>
                  </Col>
                </Row>

                <Row>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>Phone Number</Form.Label>
                      <div className="position-relative">
                        <Form.Control
                          type="tel"
                          name="phone"
                          value={userData.phone}
                          onChange={handleChange}
                          disabled={!editing}
                          isInvalid={!!errors.phone}
                          className="ps-5"
                        />
                        <FaPhone className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" />
                      </div>
                      <Form.Control.Feedback type="invalid">
                        {errors.phone}
                      </Form.Control.Feedback>
                    </Form.Group>
                  </Col>
                  
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>Age</Form.Label>
                      <Form.Control
                        type="number"
                        name="age"
                        value={userData.age}
                        onChange={handleChange}
                        disabled={!editing}
                        isInvalid={!!errors.age}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.age}
                      </Form.Control.Feedback>
                    </Form.Group>
                  </Col>
                </Row>

                <Row>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>Gender</Form.Label>
                      <Form.Select
                        name="gender"
                        value={userData.gender}
                        onChange={handleChange}
                        disabled={!editing}
                        isInvalid={!!errors.gender}
                      >
                        <option value="">Select gender</option>
                        <option value="male">Male</option>
                        <option value="female">Female</option>
                        <option value="other">Other</option>
                      </Form.Select>
                      <Form.Control.Feedback type="invalid">
                        {errors.gender}
                      </Form.Control.Feedback>
                    </Form.Group>
                  </Col>
                </Row>

                <Form.Group className="mb-4">
                  <Form.Label>Address</Form.Label>
                  <div className="position-relative">
                    <Form.Control
                      as="textarea"
                      rows={3}
                      name="address"
                      value={userData.address}
                      onChange={handleChange}
                      disabled={!editing}
                      isInvalid={!!errors.address}
                      className="ps-5"
                    />
                    <FaMapMarkerAlt className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" 
                                   style={{ top: '1.5rem' }} />
                  </div>
                  <Form.Control.Feedback type="invalid">
                    {errors.address}
                  </Form.Control.Feedback>
                </Form.Group>
              </Form>

              {editing && (
                <Alert variant="info" className="mt-3">
                  <strong>Note:</strong> Changes will be saved to your profile. Make sure all information is accurate.
                </Alert>
              )}
            </Card.Body>
          </Card>

          {/* Account Security */}
          <Card className="border-0 shadow-sm mt-4">
            <Card.Header className="bg-white border-0">
              <h5 className="mb-0">Account Security</h5>
            </Card.Header>
            <Card.Body>
              <Row>
                <Col md={6}>
                  <h6>Password</h6>
                  <p className="text-muted">Last changed: Never</p>
                  <Button variant="outline-primary" size="sm">
                    Change Password
                  </Button>
                </Col>
                <Col md={6}>
                  <h6>Two-Factor Authentication</h6>
                  <p className="text-muted">Not enabled</p>
                  <Button variant="outline-primary" size="sm">
                    Enable 2FA
                  </Button>
                </Col>
              </Row>
            </Card.Body>
          </Card>

          {/* Account Actions */}
          <Card className="border-0 shadow-sm mt-4">
            <Card.Header className="bg-white border-0">
              <h5 className="mb-0">Account Actions</h5>
            </Card.Header>
            <Card.Body>
              <Row>
                <Col md={6}>
                  <Button variant="outline-warning" className="w-100 mb-2">
                    Download My Data
                  </Button>
                </Col>
                <Col md={6}>
                  <Button variant="outline-danger" className="w-100 mb-2">
                    Delete Account
                  </Button>
                </Col>
              </Row>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default Profile;





