import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Navbar, Nav, Container, Button, Dropdown } from 'react-bootstrap';
import { useAuth } from '../contexts/AuthContext';
import { FaUser, FaSignOutAlt, FaHome, FaUserMd, FaPills, FaCalendarAlt } from 'react-icons/fa';

const NavigationBar = () => {
  const { user, isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();
  const [expanded, setExpanded] = useState(false);

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  const handleNavClick = () => {
    setExpanded(false);
  };

  return (
    <Navbar 
      bg="white" 
      expand="lg" 
      className="shadow-sm py-3"
      expanded={expanded}
      onToggle={() => setExpanded(!expanded)}
    >
      <Container>
        <Navbar.Brand as={Link} to="/" className="fw-bold fs-4">
          <span className="text-primary">Ayur</span>Setu
        </Navbar.Brand>
        
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link as={Link} to="/" onClick={handleNavClick}>
              <FaHome className="me-1" />
              Home
            </Nav.Link>
            <Nav.Link as={Link} to="/doctors" onClick={handleNavClick}>
              <FaUserMd className="me-1" />
              Doctors
            </Nav.Link>
            {isAuthenticated && (
              <>
                <Nav.Link as={Link} to="/consultation" onClick={handleNavClick}>
                  <FaCalendarAlt className="me-1" />
                  Consultations
                </Nav.Link>
                <Nav.Link as={Link} to="/pharmacy" onClick={handleNavClick}>
                  <FaPills className="me-1" />
                  Pharmacy
                </Nav.Link>
              </>
            )}
          </Nav>
          
          <Nav className="ms-auto">
            {isAuthenticated ? (
              <Dropdown align="end">
                <Dropdown.Toggle variant="outline-primary" id="dropdown-basic">
                  <FaUser className="me-1" />
                  {user?.name || user?.email || 'User'}
                </Dropdown.Toggle>

                <Dropdown.Menu>
                  <Dropdown.Item as={Link} to="/dashboard" onClick={handleNavClick}>
                    Dashboard
                  </Dropdown.Item>
                  <Dropdown.Item as={Link} to="/profile" onClick={handleNavClick}>
                    Profile
                  </Dropdown.Item>
                  <Dropdown.Divider />
                  <Dropdown.Item onClick={handleLogout}>
                    <FaSignOutAlt className="me-1" />
                    Logout
                  </Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>
            ) : (
              <div className="d-flex gap-2">
                <Button as={Link} to="/login" variant="outline-primary" size="sm">
                  Login
                </Button>
                <Button as={Link} to="/register" variant="primary" size="sm">
                  Register
                </Button>
              </div>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default NavigationBar;





