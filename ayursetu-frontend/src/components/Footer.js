import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import { FaPhone, FaEnvelope, FaMapMarkerAlt, FaFacebook, FaTwitter, FaInstagram, FaLinkedin, FaWhatsapp } from 'react-icons/fa';

const Footer = () => {
  return (
    <footer className="bg-dark text-light py-5 mt-5">
      <Container>
        <Row>
          <Col lg={4} md={6} className="mb-4">
            <h5 className="text-primary mb-3">AyurSetu</h5>
            <p className="text-muted">
              Your complete Ayurvedic healthcare platform connecting patients with qualified 
              Ayurvedic doctors and providing access to authentic medicines. Experience 
              holistic wellness with traditional Ayurvedic practices.
            </p>
            <div className="d-flex gap-3">
              <a href="#" className="text-light fs-5" title="Facebook">
                <FaFacebook />
              </a>
              <a href="#" className="text-light fs-5" title="Twitter">
                <FaTwitter />
              </a>
              <a href="#" className="text-light fs-5" title="Instagram">
                <FaInstagram />
              </a>
              <a href="#" className="text-light fs-5" title="LinkedIn">
                <FaLinkedin />
              </a>
              <a href="#" className="text-light fs-5" title="WhatsApp">
                <FaWhatsapp />
              </a>
            </div>
          </Col>
          
          <Col lg={2} md={6} className="mb-4">
            <h6 className="mb-3">Services</h6>
            <ul className="list-unstyled">
              <li><a href="/doctors" className="text-muted text-decoration-none">Find Doctors</a></li>
              <li><a href="/consultation" className="text-muted text-decoration-none">Book Consultation</a></li>
              <li><a href="/pharmacy" className="text-muted text-decoration-none">Online Pharmacy</a></li>
              <li><a href="#" className="text-muted text-decoration-none">Health Tips</a></li>
              <li><a href="#" className="text-muted text-decoration-none">Ayurvedic Remedies</a></li>
            </ul>
          </Col>
          
          <Col lg={2} md={6} className="mb-4">
            <h6 className="mb-3">Company</h6>
            <ul className="list-unstyled">
              <li><a href="#" className="text-muted text-decoration-none">About Us</a></li>
              <li><a href="#" className="text-muted text-decoration-none">Our Mission</a></li>
              <li><a href="#" className="text-muted text-decoration-none">Careers</a></li>
              <li><a href="#" className="text-muted text-decoration-none">Blog</a></li>
              <li><a href="#" className="text-muted text-decoration-none">Press</a></li>
            </ul>
          </Col>
          
          <Col lg={2} md={6} className="mb-4">
            <h6 className="mb-3">Support</h6>
            <ul className="list-unstyled">
              <li><a href="#" className="text-muted text-decoration-none">Help Center</a></li>
              <li><a href="#" className="text-muted text-decoration-none">Contact Us</a></li>
              <li><a href="#" className="text-muted text-decoration-none">Privacy Policy</a></li>
              <li><a href="#" className="text-muted text-decoration-none">Terms of Service</a></li>
              <li><a href="#" className="text-muted text-decoration-none">FAQs</a></li>
            </ul>
          </Col>
          
          <Col lg={2} md={6} className="mb-4">
            <h6 className="mb-3">Contact Info</h6>
            <div className="d-flex align-items-center mb-2">
              <FaPhone className="me-2 text-primary" />
              <span className="text-muted">+91 98765 43210</span>
            </div>
            <div className="d-flex align-items-center mb-2">
              <FaEnvelope className="me-2 text-primary" />
              <span className="text-muted">ayursetu.official@gmail.com</span>
            </div>
            <div className="d-flex align-items-center mb-2">
              <FaWhatsapp className="me-2 text-primary" />
              <span className="text-muted">+91 98765 43210</span>
            </div>
            <div className="d-flex align-items-center">
              <FaMapMarkerAlt className="me-2 text-primary" />
              <span className="text-muted">Pune, Maharashtra, India</span>
            </div>
          </Col>
        </Row>
        
        <hr className="my-4" />
        
        <Row>
          <Col md={6}>
            <p className="text-muted mb-0">
              © 2024 AyurSetu. All rights reserved.
            </p>
          </Col>
          <Col md={6} className="text-md-end">
            <p className="text-muted mb-0">
              Made with ❤️ for better healthcare
            </p>
          </Col>
        </Row>
      </Container>
    </footer>
  );
};

export default Footer;





