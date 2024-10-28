import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import logo from '../../assets/logo.svg';

const Navbar = () => {
  const navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [modalType, setModalType] = useState(""); // 'login' or 'signup'
  const [userRole, setUserRole] = useState(""); // 'student' or 'instructor'
  const [isAuthenticated, setIsAuthenticated] = useState(false); // NEW: track if the user is logged in

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  const openModal = (type) => {
    setModalType(type);
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
  };

  const handleRoleSelection = (role) => {
    setUserRole(role);

    // Simulate successful login/signup
    setIsAuthenticated(true); // Mark user as authenticated
    closeModal(); // Close the modal after login/signup

    // Navigate to the corresponding path based on user role
    if (role === "student") {
      navigate("/signin");
    } else if (role === "teacher") {
      navigate("/teacher");
    }
  };

  const handleLogout = () => {
    setIsAuthenticated(false); // Mark user as logged out
    navigate("/"); // Optionally navigate to home or login page
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
              <Link
                to="/"
                className="text-white hover:text-gray-300 px-3 py-2 rounded-md text-sm font-medium"
              >
                Home
              </Link>
              <Link
                to="/courses"
                className="text-white hover:text-gray-300 px-3 py-2 rounded-md text-sm font-medium"
              >
                Courses
              </Link>
              <Link
                to="/Guidance"
                className="text-white hover:text-gray-300 px-3 py-2 rounded-md text-sm font-medium"
              >
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
          className={`${
            isOpen ? "block" : "hidden"
          } sm:hidden absolute top-16 left-0 w-full bg-teal-500`}
        >
          <div className="px-2 pt-2 pb-3 space-y-1">
            <Link
              to="/"
              className="block px-3 py-2 rounded-md text-base font-medium text-white hover:bg-teal-600"
            >
              Home
            </Link>
            <Link
              to="/courses"
              className="block px-3 py-2 rounded-md text-base font-medium text-white hover:bg-teal-600"
            >
              Courses
            </Link>
            <Link
              to="/Guidance"
              className="block px-3 py-2 rounded-md text-base font-medium text-white hover:bg-teal-600"
            >
              Guidance
            </Link>
          </div>
        </div>
      </nav>

      {/* Modal */}
      {showModal && (
        <div className="fixed z-10 inset-0 overflow-y-auto">
          <div className="flex items-center justify-center min-h-screen px-4 text-center sm:block sm:p-0">
            <div className="fixed inset-0 transition-opacity" aria-hidden="true">
              <div className="absolute inset-0 bg-gray-500 opacity-75"></div>
            </div>

            <div className="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
              <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                <div className="sm:flex sm:items-start">
                  <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                    <h3 className="text-lg leading-6 font-medium text-gray-900" id="modal-title">
                      {modalType === "login" ? "Login as" : "Sign Up as"}
                    </h3>
                    <div className="mt-2">
                      <button
                        className="w-full bg-teal-500 text-white py-2 px-4 rounded-full mb-4"
                        onClick={() => handleRoleSelection("student")}
                      >
                        Student
                      </button>
                      <button
                        className="w-full bg-teal-500 text-white py-2 px-4 rounded-full"
                        onClick={() => handleRoleSelection("teacher")}
                      >
                        Teacher
                      </button>
                    </div>
                  </div>
                </div>
              </div>
              <div className="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                <button
                  type="button"
                  className="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-teal-600 text-base font-medium text-white hover:bg-teal-700 focus:outline-none sm:ml-3 sm:w-auto sm:text-sm"
                  onClick={closeModal}
                >
                  Close
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default Navbar;
