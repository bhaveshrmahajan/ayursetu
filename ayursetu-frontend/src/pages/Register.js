import React, { useState } from 'react';
import { Container, Row, Col, Form, Button, Card, Alert } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { toast } from 'react-toastify';
import { FaUser, FaEnvelope, FaLock, FaPhone, FaEye, FaEyeSlash, FaCalendarAlt, FaMapMarkerAlt, FaVenusMars, FaTint, FaExclamationTriangle } from 'react-icons/fa';
import { motion } from 'framer-motion';

const Register = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    phone: '',
    dateOfBirth: '',
    gender: '',
    address: '',
    city: '',
    state: '',
    country: '',
    pincode: '',
    bloodGroup: '',
    emergencyContact: '',
    role: 'PATIENT' // Default role
  });
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({});
  const [passwordStrength, setPasswordStrength] = useState(0);

  const { register } = useAuth();
  const navigate = useNavigate();

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

    // Check password strength
    if (name === 'password') {
      let strength = 0;
      if (value.length >= 8) strength++;
      if (/[A-Z]/.test(value)) strength++;
      if (/[a-z]/.test(value)) strength++;
      if (/[0-9]/.test(value)) strength++;
      if (/[^A-Za-z0-9]/.test(value)) strength++;
      setPasswordStrength(strength);
    }
  };

  const getPasswordStrengthColor = () => {
    if (passwordStrength <= 2) return 'danger';
    if (passwordStrength <= 3) return 'warning';
    return 'success';
  };

  const getPasswordStrengthText = () => {
    if (passwordStrength <= 2) return 'Weak';
    if (passwordStrength <= 3) return 'Medium';
    return 'Strong';
  };

  const validateForm = () => {
    const newErrors = {};

    if (!formData.name.trim()) {
      newErrors.name = 'Name is required';
    } else if (formData.name.trim().length < 2) {
      newErrors.name = 'Name must be at least 2 characters';
    }

    if (!formData.email) {
      newErrors.email = 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'Please enter a valid email address';
    }

    if (!formData.password) {
      newErrors.password = 'Password is required';
    } else if (formData.password.length < 8) {
      newErrors.password = 'Password must be at least 8 characters';
    } else if (passwordStrength < 3) {
      newErrors.password = 'Password is too weak';
    }

    if (!formData.confirmPassword) {
      newErrors.confirmPassword = 'Please confirm your password';
    } else if (formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = 'Passwords do not match';
    }

    if (!formData.phone) {
      newErrors.phone = 'Phone number is required';
    } else if (!/^\d{10}$/.test(formData.phone.replace(/\D/g, ''))) {
      newErrors.phone = 'Please enter a valid 10-digit phone number';
    }

    if (!formData.dateOfBirth) {
      newErrors.dateOfBirth = 'Date of Birth is required';
    }

    if (!formData.gender) {
      newErrors.gender = 'Please select your gender';
    }

    if (!formData.role) {
      newErrors.role = 'Please select your account type';
    }

    if (!formData.address.trim()) {
      newErrors.address = 'Address is required';
    } else if (formData.address.trim().length < 5) {
      newErrors.address = 'Please enter a complete address (at least 5 characters)';
    }

    if (!formData.city.trim()) {
      newErrors.city = 'City is required';
    }

    if (!formData.state.trim()) {
      newErrors.state = 'State is required';
    }

    if (!formData.country.trim()) {
      newErrors.country = 'Country is required';
    }

    if (!formData.pincode) {
      newErrors.pincode = 'Pincode is required';
    } else if (!/^\d{6}$/.test(formData.pincode)) {
      newErrors.pincode = 'Please enter a valid 6-digit pincode';
    }

    if (!formData.bloodGroup) {
      newErrors.bloodGroup = 'Blood Group is required';
    }

    if (!formData.emergencyContact) {
      newErrors.emergencyContact = 'Emergency Contact is required';
    } else if (!/^\d{10}$/.test(formData.emergencyContact.replace(/\D/g, ''))) {
      newErrors.emergencyContact = 'Please enter a valid 10-digit emergency contact number';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!validateForm()) {
      toast.error('Please fix the errors in the form');
      return;
    }

    setLoading(true);
    
    try {
      // Remove confirmPassword from the data sent to backend
      const { confirmPassword, ...registrationData } = formData;
      
      console.log('Registration data being sent:', registrationData);
      
      const result = await register(registrationData);
      
      console.log('Registration result:', result);
      
      if (result.success) {
        toast.success('Registration successful! Please login to continue.');
        navigate('/login');
      } else {
        console.error('Registration failed:', result.error);
        toast.error(result.error || 'Registration failed');
      }
    } catch (error) {
      console.error('Registration error:', error);
      toast.error('An error occurred during registration');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-vh-100 d-flex align-items-center py-5" 
         style={{ 
           background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
           minHeight: '100vh'
         }}>
      <Container>
        <Row className="justify-content-center">
          <Col lg={8} xl={6}>
            <motion.div
              initial={{ opacity: 0, y: 50 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.6 }}
            >
              <Card className="border-0 shadow-lg">
                <Card.Body className="p-5">
                  <div className="text-center mb-4">
                    <motion.div
                      initial={{ scale: 0 }}
                      animate={{ scale: 1 }}
                      transition={{ delay: 0.2, type: "spring", stiffness: 200 }}
                    >
                      <div className="bg-primary rounded-circle d-inline-flex align-items-center justify-content-center mb-3" 
                           style={{ width: '80px', height: '80px' }}>
                        <FaUser className="text-white" size={30} />
                      </div>
                    </motion.div>
                    <h2 className="text-primary fw-bold mb-2">Join AyurSetu</h2>
                    <p className="text-muted">Create your account for holistic healthcare</p>
                  </div>

                  <Form onSubmit={handleSubmit}>
                    <Row>
                      <Col md={6}>
                        <Form.Group className="mb-3">
                          <Form.Label className="fw-semibold">Full Name</Form.Label>
                          <div className="position-relative">
                            <Form.Control
                              type="text"
                              name="name"
                              value={formData.name}
                              onChange={handleChange}
                              placeholder="Enter your full name"
                              isInvalid={!!errors.name}
                              className="ps-5 border-2"
                              style={{ borderRadius: '10px' }}
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
                          <Form.Label className="fw-semibold">Email Address</Form.Label>
                          <div className="position-relative">
                            <Form.Control
                              type="email"
                              name="email"
                              value={formData.email}
                              onChange={handleChange}
                              placeholder="Enter your email"
                              isInvalid={!!errors.email}
                              className="ps-5 border-2"
                              style={{ borderRadius: '10px' }}
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
                          <Form.Label className="fw-semibold">Password</Form.Label>
                          <div className="position-relative">
                            <Form.Control
                              type={showPassword ? 'text' : 'password'}
                              name="password"
                              value={formData.password}
                              onChange={handleChange}
                              placeholder="Enter your password"
                              isInvalid={!!errors.password}
                              className="ps-5 pe-5 border-2"
                              style={{ borderRadius: '10px' }}
                            />
                            <FaLock className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" />
                            <Button
                              type="button"
                              variant="link"
                              className="position-absolute top-50 end-0 translate-middle-y p-0 me-3 text-muted"
                              onClick={() => setShowPassword(!showPassword)}
                            >
                              {showPassword ? <FaEyeSlash /> : <FaEye />}
                            </Button>
                          </div>
                          {formData.password && (
                            <div className="mt-2">
                              <div className="d-flex align-items-center">
                                <div className="progress flex-grow-1 me-2" style={{ height: '6px' }}>
                                  <div 
                                    className={`progress-bar bg-${getPasswordStrengthColor()}`}
                                    style={{ width: `${(passwordStrength / 5) * 100}%` }}
                                  ></div>
                                </div>
                                <small className={`text-${getPasswordStrengthColor()}`}>
                                  {getPasswordStrengthText()}
                                </small>
                              </div>
                            </div>
                          )}
                          <Form.Control.Feedback type="invalid">
                            {errors.password}
                          </Form.Control.Feedback>
                        </Form.Group>
                      </Col>
                      
                      <Col md={6}>
                        <Form.Group className="mb-3">
                          <Form.Label className="fw-semibold">Confirm Password</Form.Label>
                          <div className="position-relative">
                            <Form.Control
                              type={showConfirmPassword ? 'text' : 'password'}
                              name="confirmPassword"
                              value={formData.confirmPassword}
                              onChange={handleChange}
                              placeholder="Confirm your password"
                              isInvalid={!!errors.confirmPassword}
                              className="ps-5 pe-5 border-2"
                              style={{ borderRadius: '10px' }}
                            />
                            <FaLock className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" />
                            <Button
                              type="button"
                              variant="link"
                              className="position-absolute top-50 end-0 translate-middle-y p-0 me-3 text-muted"
                              onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                            >
                              {showConfirmPassword ? <FaEyeSlash /> : <FaEye />}
                            </Button>
                          </div>
                          <Form.Control.Feedback type="invalid">
                            {errors.confirmPassword}
                          </Form.Control.Feedback>
                        </Form.Group>
                      </Col>
                    </Row>

                    <Row>
                      <Col md={6}>
                        <Form.Group className="mb-3">
                          <Form.Label className="fw-semibold">Phone Number</Form.Label>
                          <div className="position-relative">
                            <Form.Control
                              type="tel"
                              name="phone"
                              value={formData.phone}
                              onChange={handleChange}
                              placeholder="Enter your phone number"
                              isInvalid={!!errors.phone}
                              className="ps-5 border-2"
                              style={{ borderRadius: '10px' }}
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
                          <Form.Label className="fw-semibold">Date of Birth</Form.Label>
                          <div className="position-relative">
                            <Form.Control
                              type="date"
                              name="dateOfBirth"
                              value={formData.dateOfBirth}
                              onChange={handleChange}
                              isInvalid={!!errors.dateOfBirth}
                              className="ps-5 border-2"
                              style={{ borderRadius: '10px' }}
                            />
                            <FaCalendarAlt className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" />
                          </div>
                          <Form.Control.Feedback type="invalid">
                            {errors.dateOfBirth}
                          </Form.Control.Feedback>
                        </Form.Group>
                      </Col>
                    </Row>

                    <Row>
                      <Col md={6}>
                        <Form.Group className="mb-3">
                          <Form.Label className="fw-semibold">Gender</Form.Label>
                          <div className="position-relative">
                            <Form.Select
                              name="gender"
                              value={formData.gender}
                              onChange={handleChange}
                              isInvalid={!!errors.gender}
                              className="ps-5 border-2"
                              style={{ borderRadius: '10px' }}
                            >
                              <option value="">Select gender</option>
                              <option value="MALE">Male</option>
                              <option value="FEMALE">Female</option>
                              <option value="OTHER">Other</option>
                            </Form.Select>
                            <FaVenusMars className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" 
                                         style={{ zIndex: 10, pointerEvents: 'none' }} />
                          </div>
                          <Form.Control.Feedback type="invalid">
                            {errors.gender}
                          </Form.Control.Feedback>
                        </Form.Group>
                      </Col>
                      
                      <Col md={6}>
                        <Form.Group className="mb-3">
                          <Form.Label className="fw-semibold">Account Type</Form.Label>
                          <div className="position-relative">
                            <Form.Select
                              name="role"
                              value={formData.role}
                              onChange={handleChange}
                              isInvalid={!!errors.role}
                              className="ps-5 border-2"
                              style={{ borderRadius: '10px' }}
                            >
                              <option value="">Select account type</option>
                              <option value="PATIENT">Patient</option>
                              <option value="DOCTOR">Doctor</option>
                              <option value="ADMIN">Admin</option>
                            </Form.Select>
                            <FaUser className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" 
                                     style={{ zIndex: 10, pointerEvents: 'none' }} />
                          </div>
                          <Form.Control.Feedback type="invalid">
                            {errors.role}
                          </Form.Control.Feedback>
                        </Form.Group>
                      </Col>
                    </Row>

                    <Row>
                      <Col md={6}>
                        <Form.Group className="mb-3">
                          <Form.Label className="fw-semibold">Address</Form.Label>
                          <div className="position-relative">
                            <Form.Control
                              type="text"
                              name="address"
                              value={formData.address}
                              onChange={handleChange}
                              placeholder="Enter your street address"
                              isInvalid={!!errors.address}
                              className="ps-5 border-2"
                              style={{ borderRadius: '10px' }}
                            />
                            <FaMapMarkerAlt className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" />
                          </div>
                          <Form.Control.Feedback type="invalid">
                            {errors.address}
                          </Form.Control.Feedback>
                        </Form.Group>
                      </Col>
                      
                      <Col md={6}>
                        <Form.Group className="mb-3">
                          <Form.Label className="fw-semibold">City</Form.Label>
                          <div className="position-relative">
                            <Form.Control
                              type="text"
                              name="city"
                              value={formData.city}
                              onChange={handleChange}
                              placeholder="Enter your city"
                              isInvalid={!!errors.city}
                              className="ps-5 border-2"
                              style={{ borderRadius: '10px' }}
                            />
                            <FaMapMarkerAlt className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" />
                          </div>
                          <Form.Control.Feedback type="invalid">
                            {errors.city}
                          </Form.Control.Feedback>
                        </Form.Group>
                      </Col>
                    </Row>

                    <Row>
                      <Col md={6}>
                        <Form.Group className="mb-3">
                          <Form.Label className="fw-semibold">State</Form.Label>
                          <div className="position-relative">
                            <Form.Control
                              type="text"
                              name="state"
                              value={formData.state}
                              onChange={handleChange}
                              placeholder="Enter your state"
                              isInvalid={!!errors.state}
                              className="ps-5 border-2"
                              style={{ borderRadius: '10px' }}
                            />
                            <FaMapMarkerAlt className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" />
                          </div>
                          <Form.Control.Feedback type="invalid">
                            {errors.state}
                          </Form.Control.Feedback>
                        </Form.Group>
                      </Col>
                      
                      <Col md={6}>
                        <Form.Group className="mb-3">
                          <Form.Label className="fw-semibold">Country</Form.Label>
                          <div className="position-relative">
                            <Form.Control
                              type="text"
                              name="country"
                              value={formData.country}
                              onChange={handleChange}
                              placeholder="Enter your country"
                              isInvalid={!!errors.country}
                              className="ps-5 border-2"
                              style={{ borderRadius: '10px' }}
                            />
                            <FaMapMarkerAlt className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" />
                          </div>
                          <Form.Control.Feedback type="invalid">
                            {errors.country}
                          </Form.Control.Feedback>
                        </Form.Group>
                      </Col>
                    </Row>

                    <Row>
                      <Col md={6}>
                        <Form.Group className="mb-3">
                          <Form.Label className="fw-semibold">Pincode</Form.Label>
                          <div className="position-relative">
                            <Form.Control
                              type="text"
                              name="pincode"
                              value={formData.pincode}
                              onChange={handleChange}
                              placeholder="Enter your pincode"
                              isInvalid={!!errors.pincode}
                              className="ps-5 border-2"
                              style={{ borderRadius: '10px' }}
                            />
                            <FaMapMarkerAlt className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" />
                          </div>
                          <Form.Control.Feedback type="invalid">
                            {errors.pincode}
                          </Form.Control.Feedback>
                        </Form.Group>
                      </Col>
                      
                      <Col md={6}>
                        <Form.Group className="mb-3">
                          <Form.Label className="fw-semibold">Blood Group</Form.Label>
                          <div className="position-relative">
                            <Form.Select
                              name="bloodGroup"
                              value={formData.bloodGroup}
                              onChange={handleChange}
                              isInvalid={!!errors.bloodGroup}
                              className="ps-5 border-2"
                              style={{ borderRadius: '10px' }}
                            >
                              <option value="">Select blood group</option>
                              <option value="A+">A+</option>
                              <option value="A-">A-</option>
                              <option value="B+">B+</option>
                              <option value="B-">B-</option>
                              <option value="AB+">AB+</option>
                              <option value="AB-">AB-</option>
                              <option value="O+">O+</option>
                              <option value="O-">O-</option>
                            </Form.Select>
                            <FaTint className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" />
                          </div>
                          <Form.Control.Feedback type="invalid">
                            {errors.bloodGroup}
                          </Form.Control.Feedback>
                        </Form.Group>
                      </Col>
                    </Row>

                    <Row>
                      <Col md={6}>
                        <Form.Group className="mb-3">
                          <Form.Label className="fw-semibold">Emergency Contact</Form.Label>
                          <div className="position-relative">
                            <Form.Control
                              type="tel"
                              name="emergencyContact"
                              value={formData.emergencyContact}
                              onChange={handleChange}
                              placeholder="Enter your emergency contact number"
                              isInvalid={!!errors.emergencyContact}
                              className="ps-5 border-2"
                              style={{ borderRadius: '10px' }}
                            />
                            <FaExclamationTriangle className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" />
                          </div>
                          <Form.Control.Feedback type="invalid">
                            {errors.emergencyContact}
                          </Form.Control.Feedback>
                        </Form.Group>
                      </Col>
                    </Row>

                    <Alert variant="info" className="mb-4">
                      <small>
                        <strong>Note:</strong> By creating an account, you agree to our Terms of Service and Privacy Policy. 
                        Your information will be used to provide you with personalized healthcare services.
                      </small>
                    </Alert>

                    <motion.div
                      whileHover={{ scale: 1.02 }}
                      whileTap={{ scale: 0.98 }}
                    >
                      <Button
                        type="submit"
                        variant="primary"
                        size="lg"
                        className="w-100 mb-3 fw-bold"
                        disabled={loading}
                        style={{ 
                          borderRadius: '10px',
                          padding: '12px',
                          background: 'linear-gradient(45deg, #667eea, #764ba2)',
                          border: 'none'
                        }}
                      >
                        {loading ? (
                          <>
                            <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                            Creating Account...
                          </>
                        ) : (
                          'Create Account'
                        )}
                      </Button>
                    </motion.div>

                    <div className="text-center">
                      <p className="mb-0 text-muted">
                        Already have an account?{' '}
                        <Link to="/login" className="text-primary text-decoration-none fw-bold">
                          Sign in here
                        </Link>
                      </p>
                    </div>
                  </Form>
                </Card.Body>
              </Card>
            </motion.div>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default Register;





