import React from 'react';
import { Container, Row, Col, Button, Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import { 
  FaUserMd, 
  FaPills, 
  FaCalendarAlt, 
  FaHeart, 
  FaShieldAlt, 
  FaClock,
  FaStar,
  FaUsers,
  FaLeaf
} from 'react-icons/fa';

const Home = () => {
  const features = [
    {
      icon: <FaUserMd />,
      title: "Expert Ayurvedic Doctors",
      description: "Connect with qualified and experienced Ayurvedic practitioners"
    },
    {
      icon: <FaPills />,
      title: "Authentic Medicines",
      description: "Access to genuine Ayurvedic medicines and supplements"
    },
    {
      icon: <FaCalendarAlt />,
      title: "Easy Consultation Booking",
      description: "Book appointments with doctors at your convenience"
    },
    {
      icon: <FaHeart />,
      title: "Holistic Healthcare",
      description: "Complete wellness solutions based on ancient Ayurvedic principles"
    },
    {
      icon: <FaShieldAlt />,
      title: "Secure & Private",
      description: "Your health information is protected with highest security standards"
    },
    {
      icon: <FaClock />,
      title: "24/7 Support",
      description: "Round the clock customer support for all your healthcare needs"
    }
  ];

  const stats = [
    { number: "500+", label: "Expert Doctors" },
    { number: "10K+", label: "Happy Patients" },
    { number: "1000+", label: "Medicines" },
    { number: "50+", label: "Cities Covered" }
  ];

  return (
    <div>
      {/* Hero Section */}
      <section className="hero-section">
        <Container>
          <Row className="align-items-center">
            <Col lg={6} className="mb-4">
              <motion.div
                initial={{ opacity: 0, x: -50 }}
                animate={{ opacity: 1, x: 0 }}
                transition={{ duration: 0.8 }}
              >
                <h1 className="display-4 fw-bold mb-4">
                  Your Complete <span className="text-warning">Ayurvedic</span> Healthcare Platform
                </h1>
                <p className="lead mb-4">
                  Connect with qualified Ayurvedic doctors, book consultations, and access authentic 
                  medicines all in one place. Experience holistic wellness the traditional way.
                </p>
                <div className="d-flex gap-3 flex-wrap">
                  <Button as={Link} to="/doctors" size="lg" variant="warning">
                    Find Doctors
                  </Button>
                  <Button as={Link} to="/register" size="lg" variant="outline-light">
                    Get Started
                  </Button>
                </div>
              </motion.div>
            </Col>
            <Col lg={6}>
              <motion.div
                initial={{ opacity: 0, x: 50 }}
                animate={{ opacity: 1, x: 0 }}
                transition={{ duration: 0.8, delay: 0.2 }}
                className="text-center"
              >
                <div className="position-relative">
                  <div className="bg-white rounded-circle d-inline-flex align-items-center justify-content-center" 
                       style={{ width: '300px', height: '300px' }}>
                    <FaLeaf className="text-primary" style={{ fontSize: '8rem' }} />
                  </div>
                </div>
              </motion.div>
            </Col>
          </Row>
        </Container>
      </section>

      {/* Stats Section */}
      <section className="py-5 bg-light">
        <Container>
          <Row>
            {stats.map((stat, index) => (
              <Col key={index} md={3} className="text-center mb-4">
                <motion.div
                  initial={{ opacity: 0, y: 20 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ duration: 0.5, delay: index * 0.1 }}
                >
                  <h2 className="text-primary fw-bold">{stat.number}</h2>
                  <p className="text-muted">{stat.label}</p>
                </motion.div>
              </Col>
            ))}
          </Row>
        </Container>
      </section>

      {/* Features Section */}
      <section className="py-5">
        <Container>
          <Row className="mb-5">
            <Col className="text-center">
              <h2 className="section-title">Why Choose AyurSetu?</h2>
              <p className="text-muted">Experience the best of Ayurvedic healthcare with our comprehensive platform</p>
            </Col>
          </Row>
          <Row>
            {features.map((feature, index) => (
              <Col key={index} lg={4} md={6} className="mb-4">
                <motion.div
                  initial={{ opacity: 0, y: 30 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ duration: 0.5, delay: index * 0.1 }}
                >
                  <Card className="feature-card h-100 border-0">
                    <Card.Body className="text-center">
                      <div className="feature-icon">
                        {feature.icon}
                      </div>
                      <h5 className="mb-3">{feature.title}</h5>
                      <p className="text-muted mb-0">{feature.description}</p>
                    </Card.Body>
                  </Card>
                </motion.div>
              </Col>
            ))}
          </Row>
        </Container>
      </section>

      {/* CTA Section */}
      <section className="py-5 bg-primary text-white">
        <Container>
          <Row className="align-items-center">
            <Col lg={8}>
              <h2 className="mb-3">Ready to Start Your Wellness Journey?</h2>
              <p className="lead mb-0">
                Join thousands of patients who trust AyurSetu for their healthcare needs.
              </p>
            </Col>
            <Col lg={4} className="text-lg-end">
              <Button as={Link} to="/register" size="lg" variant="warning">
                Get Started Today
              </Button>
            </Col>
          </Row>
        </Container>
      </section>

      {/* Testimonials Section */}
      <section className="py-5">
        <Container>
          <Row className="mb-5">
            <Col className="text-center">
              <h2 className="section-title">What Our Patients Say</h2>
            </Col>
          </Row>
          <Row>
            <Col md={4} className="mb-4">
              <Card className="h-100 border-0">
                <Card.Body>
                  <div className="d-flex mb-3">
                    {[...Array(5)].map((_, i) => (
                      <FaStar key={i} className="text-warning" />
                    ))}
                  </div>
                  <p className="mb-3">
                    "AyurSetu helped me find the perfect Ayurvedic doctor for my chronic condition. 
                    The consultation was thorough and the medicines prescribed were authentic."
                  </p>
                  <div className="d-flex align-items-center">
                    <div className="bg-primary rounded-circle me-3" style={{ width: '40px', height: '40px' }}></div>
                    <div>
                      <h6 className="mb-0">Priya Sharma</h6>
                      <small className="text-muted">Mumbai</small>
                    </div>
                  </div>
                </Card.Body>
              </Card>
            </Col>
            <Col md={4} className="mb-4">
              <Card className="h-100 border-0">
                <Card.Body>
                  <div className="d-flex mb-3">
                    {[...Array(5)].map((_, i) => (
                      <FaStar key={i} className="text-warning" />
                    ))}
                  </div>
                  <p className="mb-3">
                    "The online consultation feature is amazing! I could consult with a specialist 
                    from the comfort of my home. Highly recommended!"
                  </p>
                  <div className="d-flex align-items-center">
                    <div className="bg-primary rounded-circle me-3" style={{ width: '40px', height: '40px' }}></div>
                    <div>
                      <h6 className="mb-0">Rajesh Kumar</h6>
                      <small className="text-muted">Delhi</small>
                    </div>
                  </div>
                </Card.Body>
              </Card>
            </Col>
            <Col md={4} className="mb-4">
              <Card className="h-100 border-0">
                <Card.Body>
                  <div className="d-flex mb-3">
                    {[...Array(5)].map((_, i) => (
                      <FaStar key={i} className="text-warning" />
                    ))}
                  </div>
                  <p className="mb-3">
                    "The pharmacy section has a great collection of authentic Ayurvedic medicines. 
                    Delivery was prompt and packaging was excellent."
                  </p>
                  <div className="d-flex align-items-center">
                    <div className="bg-primary rounded-circle me-3" style={{ width: '40px', height: '40px' }}></div>
                    <div>
                      <h6 className="mb-0">Anita Patel</h6>
                      <small className="text-muted">Bangalore</small>
                    </div>
                  </div>
                </Card.Body>
              </Card>
            </Col>
          </Row>
        </Container>
      </section>
    </div>
  );
};

export default Home;





