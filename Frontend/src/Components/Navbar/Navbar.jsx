import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import logo from '../../assets/logo.svg';

const Navbar = () => {
  const navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(false);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userRole, setUserRole] = useState(""); // Track user role for display

  useEffect(() => {
    // Check if the user is authenticated (e.g., based on a token or session)
    const token = localStorage.getItem("authToken");
    if (token) {
      setIsAuthenticated(true);
      setUserRole(localStorage.getItem("userRole") || "User"); // Fetch role if stored
    }
  }, []);

  const toggleMenu = () => setIsOpen(!isOpen);

  const handleLogout = () => {
    setIsAuthenticated(false);
    localStorage.removeItem("authToken"); // Clear token
    localStorage.removeItem("userRole"); // Clear role
    navigate("/"); // Optionally navigate to the homepage after logout
  };

  const openModal = (type) => {
    if (type === "login") {
      navigate("/signin"); // Navigate to the login page
    } else if (type === "signup") {
      navigate("/signin"); // Navigate to the signup page
    }
  };

  return (
      <>
        <nav className="bg-teal-500">
          <div className="max-w-7xl mx-auto px-2 sm:px-6 lg:px-8">
            <div className="relative flex items-center justify-between h-16">
              <div className="flex-shrink-0">
                <Link to="/">
                  <img className="h-12 w-auto" src={logo} alt="IGELP" />
                </Link>
              </div>

              <div className="hidden sm:flex sm:items-center sm:space-x-8">
                <Link to="/" className="text-white hover:text-gray-300 px-3 py-2 rounded-md text-sm font-medium">
                  Home
                </Link>
                <Link to="/courses" className="text-white hover:text-gray-300 px-3 py-2 rounded-md text-sm font-medium">
                  Courses
                </Link>
                <Link to="/guidance" className="text-white hover:text-gray-300 px-3 py-2 rounded-md text-sm font-medium">
                  Guidance
                </Link>
              </div>

              <div className="hidden sm:flex sm:items-center space-x-4">
                {!isAuthenticated ? (
                    <>
                      <button
                          className="text-gray-700 bg-white px-4 py-2 rounded-full text-sm font-medium hover:bg-gray-100"
                          onClick={() => openModal("login")}
                      >
                        Login
                      </button>
                      <button
                          className="bg-teal-600 text-white px-4 py-2 rounded-full text-sm font-medium hover:bg-teal-700"
                          onClick={() => openModal("signup")}
                      >
                        Sign Up
                      </button>
                    </>
                ) : (
                    <>
                      <span className="text-white mr-4">Welcome, {userRole}</span>
                      <button
                          className="bg-red-500 text-white px-4 py-2 rounded-full text-sm font-medium hover:bg-red-700"
                          onClick={handleLogout}
                      >
                        Logout
                      </button>
                    </>
                )}
              </div>

              <div className="sm:hidden flex items-center">
                <button
                    onClick={toggleMenu}
                    className="text-white hover:text-gray-300 focus:outline-none"
                >
                  <svg
                      className="h-6 w-6"
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke="currentColor"
                  >
                    <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        d={isOpen ? "M6 18L18 6M6 6l12 12" : "M4 6h16M4 12h16M4 18h16"}
                    />
                  </svg>
                </button>
              </div>
            </div>
          </div>

          {/* Mobile Menu */}
          <div
              className={`${isOpen ? "block" : "hidden"} sm:hidden absolute top-16 left-0 w-full bg-teal-500`}
          >
            <div className="px-2 pt-2 pb-3 space-y-1">
              <Link to="/" className="block px-3 py-2 rounded-md text-base font-medium text-white hover:bg-teal-600">
                Home
              </Link>
              <Link to="/courses" className="block px-3 py-2 rounded-md text-base font-medium text-white hover:bg-teal-600">
                Courses
              </Link>
              <Link to="/guidance" className="block px-3 py-2 rounded-md text-base font-medium text-white hover:bg-teal-600">
                Guidance
              </Link>
            </div>
          </div>
        </nav>
      </>
  );
};

export default Navbar;
