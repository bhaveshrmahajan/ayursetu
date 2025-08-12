import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Button, Form, InputGroup, Badge, Spinner } from 'react-bootstrap';
import { pharmacyAPI } from '../services/api';
import { toast } from 'react-toastify';
import { FaSearch, FaPills, FaShoppingCart, FaStar, FaEye } from 'react-icons/fa';

const Pharmacy = () => {
  const [medicines, setMedicines] = useState([]);
  const [filteredMedicines, setFilteredMedicines] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('');
  const [categories, setCategories] = useState([]);
  const [cart, setCart] = useState([]);

  useEffect(() => {
    fetchMedicines();
  }, []);

  useEffect(() => {
    filterMedicines();
  }, [medicines, searchTerm, selectedCategory]);

  const fetchMedicines = async () => {
    try {
      const response = await pharmacyAPI.getAllMedicines();
      const medicinesData = response.data;
      setMedicines(medicinesData);
      setFilteredMedicines(medicinesData);
      
      // Extract unique categories
      const uniqueCategories = [...new Set(medicinesData.map(medicine => medicine.category))];
      setCategories(uniqueCategories);
    } catch (error) {
      toast.error('Failed to fetch medicines');
      console.error('Error fetching medicines:', error);
    } finally {
      setLoading(false);
    }
  };

  const filterMedicines = () => {
    let filtered = medicines;

    // Filter by search term
    if (searchTerm) {
      filtered = filtered.filter(medicine =>
        medicine.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        medicine.description?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        medicine.category?.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }

    // Filter by category
    if (selectedCategory) {
      filtered = filtered.filter(medicine =>
        medicine.category === selectedCategory
      );
    }

    setFilteredMedicines(filtered);
  };

  const clearFilters = () => {
    setSearchTerm('');
    setSelectedCategory('');
  };

  const addToCart = (medicine) => {
    const existingItem = cart.find(item => item.id === medicine.id);
    
    if (existingItem) {
      setCart(cart.map(item =>
        item.id === medicine.id
          ? { ...item, quantity: item.quantity + 1 }
          : item
      ));
    } else {
      setCart([...cart, { ...medicine, quantity: 1 }]);
    }
    
    toast.success(`${medicine.name} added to cart`);
  };

  const getRatingStars = (rating) => {
    return Array.from({ length: 5 }, (_, i) => (
      <FaStar
        key={i}
        className={i < rating ? 'text-warning' : 'text-muted'}
      />
    ));
  };

  const getStockStatus = (stock) => {
    if (stock > 10) {
      return <Badge bg="success">In Stock</Badge>;
    } else if (stock > 0) {
      return <Badge bg="warning">Low Stock</Badge>;
    } else {
      return <Badge bg="danger">Out of Stock</Badge>;
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
          <h1 className="section-title">Ayurvedic Pharmacy</h1>
          <p className="text-muted">Discover authentic Ayurvedic medicines and supplements</p>
        </Col>
      </Row>

      {/* Search and Filters */}
      <Row className="mb-4">
        <Col lg={12}>
          <Card className="border-0 shadow-sm">
            <Card.Body>
              <Row>
                <Col md={6} className="mb-3">
                  <InputGroup>
                    <InputGroup.Text>
                      <FaSearch />
                    </InputGroup.Text>
                    <Form.Control
                      type="text"
                      placeholder="Search medicines, categories, or descriptions..."
                      value={searchTerm}
                      onChange={(e) => setSearchTerm(e.target.value)}
                    />
                  </InputGroup>
                </Col>
                <Col md={4} className="mb-3">
                  <Form.Select
                    value={selectedCategory}
                    onChange={(e) => setSelectedCategory(e.target.value)}
                  >
                    <option value="">All Categories</option>
                    {categories.map((category, index) => (
                      <option key={index} value={category}>{category}</option>
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
            Found {filteredMedicines.length} medicine{filteredMedicines.length !== 1 ? 's' : ''}
            {searchTerm || selectedCategory ? ' matching your criteria' : ''}
          </p>
        </Col>
      </Row>

      {/* Medicines Grid */}
      <Row>
        {filteredMedicines.length === 0 ? (
          <Col className="text-center py-5">
            <FaPills className="text-muted mb-3" style={{ fontSize: '4rem' }} />
            <h4 className="text-muted">No medicines found</h4>
            <p className="text-muted">Try adjusting your search criteria</p>
          </Col>
        ) : (
          filteredMedicines.map((medicine) => (
            <Col key={medicine.id} lg={4} md={6} className="mb-4">
              <Card className="h-100 border-0 shadow-sm">
                <Card.Body>
                  <div className="d-flex align-items-start mb-3">
                    <div className="bg-warning rounded-circle me-3 d-flex align-items-center justify-content-center" 
                         style={{ width: '60px', height: '60px' }}>
                      <FaPills className="text-white" />
                    </div>
                    <div className="flex-grow-1">
                      <h5 className="mb-1">{medicine.name}</h5>
                      <p className="text-muted mb-1">{medicine.category}</p>
                      <div className="d-flex align-items-center mb-2">
                        {getRatingStars(medicine.rating || 0)}
                        <span className="ms-2 text-muted">({medicine.rating || 0})</span>
                      </div>
                    </div>
                  </div>

                  <div className="mb-3">
                    <p className="text-muted small mb-2">
                      {medicine.description || 'Traditional Ayurvedic medicine for holistic wellness.'}
                    </p>
                    <div className="d-flex justify-content-between align-items-center mb-2">
                      <span className="fw-bold text-primary">₹{medicine.price}</span>
                      {getStockStatus(medicine.stock)}
                    </div>
                    <small className="text-muted">
                      Stock: {medicine.stock} units available
                    </small>
                  </div>

                  <div className="d-flex gap-2">
                    <Button
                      variant="primary"
                      size="sm"
                      className="flex-grow-1"
                      onClick={() => addToCart(medicine)}
                      disabled={medicine.stock === 0}
                    >
                      <FaShoppingCart className="me-1" />
                      Add to Cart
                    </Button>
                    <Button
                      variant="outline-primary"
                      size="sm"
                    >
                      <FaEye className="me-1" />
                      View
                    </Button>
                  </div>
                </Card.Body>
              </Card>
            </Col>
          ))
        )}
      </Row>

      {/* Shopping Cart Summary */}
      {cart.length > 0 && (
        <Row className="mt-4">
          <Col>
            <Card className="border-0 shadow-sm">
              <Card.Header className="bg-white border-0">
                <h5 className="mb-0">Shopping Cart ({cart.length} items)</h5>
              </Card.Header>
              <Card.Body>
                <Row>
                  <Col md={8}>
                    {cart.map((item) => (
                      <div key={item.id} className="d-flex justify-content-between align-items-center py-2 border-bottom">
                        <div>
                          <h6 className="mb-1">{item.name}</h6>
                          <small className="text-muted">{item.category}</small>
                        </div>
                        <div className="text-end">
                          <div className="d-flex align-items-center gap-2">
                            <span className="fw-bold">₹{item.price}</span>
                            <span className="text-muted">x {item.quantity}</span>
                            <span className="fw-bold">₹{item.price * item.quantity}</span>
                          </div>
                        </div>
                      </div>
                    ))}
                  </Col>
                  <Col md={4}>
                    <div className="text-center">
                      <h5 className="mb-3">Total: ₹{cart.reduce((sum, item) => sum + (item.price * item.quantity), 0)}</h5>
                      <Button variant="success" size="lg" className="w-100 mb-2">
                        Proceed to Checkout
                      </Button>
                      <Button variant="outline-secondary" size="sm" className="w-100">
                        Clear Cart
                      </Button>
                    </div>
                  </Col>
                </Row>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      )}
    </Container>
  );
};

export default Pharmacy;





