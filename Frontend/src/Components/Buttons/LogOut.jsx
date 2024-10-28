import React from "react";

const LogOut = ({ onLogin }) => {
    // const handleLogout = () => {
    //   // Perform logout logic here
    //   // For example, redirect to the login page
    //   window.location.href = "/login";
    // };
  
  return (
    <nav className="bg-teal-400 p-4">
      <div className="container mx-auto flex justify-between items-center">
        {/* Logo */}
        <div className="flex items-center">
          <img src="logo.png" alt="Logo" className="h-8 mr-2" />
          <span className="font-bold text-white text-xl">IGELP</span>
        </div>

        {/* Search Bar */}
        <div className="flex-1 mx-4">
          <input
            type="text"
            placeholder="Search..."
            className="w-full px-4 py-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-600"
          />
        </div>

        {/* Navigation Links */}
        <div className="flex items-center space-x-4 text-white font-semibold">
          <a href="#" className="hover:text-teal-900">
            Home
          </a>
          <a href="#" className="hover:text-teal-900">
            Courses
          </a>
          <a href="#" className="hover:text-teal-900">
            Guidance
          </a>
        </div>

        {/* User Profile */}
        <div className="relative">
          <img
            src="https://randomuser.me/api/portraits/men/32.jpg"
            alt="User Avatar"
            className="w-10 h-10 rounded-full border-2 border-white"
          />
          <span className="ml-2 text-white">Rehman</span>
          {
            // Add logout button
            // <div className="absolute right-0 mt-2 bg-white rounded-lg shadow-lg p-4">
            //   <button
            //     className="block w-full text-left text-gray-700 hover:bg-gray-100 px-4 py-2"
            //     onClick={handleLogout}
            //   >
            //     Log Out
            //   </button>
            // </div>
            <button onClick={onLogin}>Login / Register</button>
          }


        </div>
      </div>
    </nav>
  );
};

export default LogOut;
