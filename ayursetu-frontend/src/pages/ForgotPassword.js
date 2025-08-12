import React, { useState } from 'react';
import { Container, Row, Col, Form, Button, Card, Alert } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import { FaEnvelope, FaArrowLeft } from 'react-icons/fa';
import { motion } from 'framer-motion';
import api from '../services/api';

const ForgotPassword = () => {
  const [email, setEmail] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!email.trim()) {
      setError('Please enter your email address');
      return;
    }

    if (!/\S+@\S+\.\S+/.test(email)) {
      setError('Please enter a valid email address');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const response = await api.post('/api/users/forgot-password', null, {
        params: { email: email.trim() }
      });

      if (response.status === 200) {
        setSuccess(true);
        toast.success('Password reset link sent to your email!');
      }
    } catch (error) {
      console.error('Forgot password error:', error);
      const errorMessage = error.response?.data?.message || 'Failed to send reset email. Please try again.';
      setError(errorMessage);
      toast.error(errorMessage);
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
          <Col lg={6} xl={4}>
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
                        <FaEnvelope className="text-white" size={30} />
                      </div>
                    </motion.div>
                    <h2 className="text-primary fw-bold mb-2">Forgot Password?</h2>
                    <p className="text-muted">
                      Enter your email address and we'll send you a link to reset your password.
                    </p>
                  </div>

                  {success ? (
                    <Alert variant="success" className="mb-4">
                      <h6 className="alert-heading">Check Your Email!</h6>
                      <p className="mb-0">
                        We've sent a password reset link to <strong>{email}</strong>. 
                        Please check your email and follow the instructions to reset your password.
                      </p>
                    </Alert>
                  ) : (
                    <Form onSubmit={handleSubmit}>
                      {error && (
                        <Alert variant="danger" className="mb-3">
                          {error}
                        </Alert>
                      )}

                      <Form.Group className="mb-4">
                        <Form.Label className="fw-semibold">Email Address</Form.Label>
                        <div className="position-relative">
                          <Form.Control
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="Enter your email address"
                            className="ps-5 border-2"
                            style={{ borderRadius: '10px' }}
                            disabled={loading}
                          />
                          <FaEnvelope className="position-absolute top-50 start-0 translate-middle-y ms-3 text-muted" />
                        </div>
                      </Form.Group>

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
                              Sending Reset Link...
                            </>
                          ) : (
                            'Send Reset Link'
                          )}
                        </Button>
                      </motion.div>

                      <div className="text-center">
                        <Link to="/login" className="text-muted text-decoration-none">
                          <FaArrowLeft className="me-2" />
                          Back to Login
                        </Link>
                      </div>
                    </Form>
                  )}

                  <div className="text-center mt-4">
                    <p className="text-muted mb-0">
                      Remember your password?{' '}
                      <Link to="/login" className="text-primary text-decoration-none fw-bold">
                        Sign in here
                      </Link>
                    </p>
                  </div>
                </Card.Body>
              </Card>
            </motion.div>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default ForgotPassword;
