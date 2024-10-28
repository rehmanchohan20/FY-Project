import React, { useState } from "react";
import Teacher from "../../assets/teacher.jpg";

const TeacherLoginSignup = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [showPassword, setShowPassword] = useState(false); // State to toggle password visibility

  // Toggle between Login and Signup forms
  const toggleForm = () => {
    setIsLogin(!isLogin);
    
  };

  // Toggle password visibility
  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  // Handle form submission for both login and signup
  const handleSubmit = (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);

    const data = {
      email: formData.get("email"),
      username: formData.get("username"), // Only for signup
      password: formData.get("password"),
    };

    if (isLogin) {
      // Perform login logic
      console.log("Login data: ", data);
    } else {
      // Perform signup logic
      console.log("Signup data: ", data);
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-gray-100">
      <div className="flex max-w-4xl w-full bg-white shadow-lg rounded-lg overflow-hidden">
        {/* Left side: Image */}
        <div
          className="w-1/2 bg-cover"
          style={{
            backgroundImage: `url(${Teacher})`,
          }}
        >
          <div className="flex flex-col justify-center items-center h-full p-8">
            <h2 className="text-white text-3xl font-bold mb-4">
              Start your career with professionals
            </h2>
          </div>
        </div>

        {/* Right side: Login/Signup Form */}
        <div className="w-1/2 p-8">
          <div className="text-center mb-4">
            <button className="px-4 py-2 align-baseline bg-teal-500 text-white font-semibold focus:outline-none">
              TEACHER
            </button>
          </div>

          <div className="flex justify-center my-6">
            {/* Login Button */}
            <button
              onClick={toggleForm}
              className={`px-4 py-2 rounded ${
                isLogin
                  ? "bg-teal-500 text-white"
                  : "bg-teal-100 text-teal-700 hover:bg-teal-200"
              }`}
            >
              Login
            </button>

            {/* Register Button */}
            <button
              onClick={toggleForm}
              className={`px-4 py-2 rounded ${
                !isLogin
                  ? "bg-teal-500 text-white"
                  : "bg-teal-100 text-teal-700 hover:bg-teal-200"
              }`}
            >
              Register
            </button>
          </div>

          {/* Form for Login/Signup */}
          <form className="mt-6" onSubmit={handleSubmit}>
            <div>
              <label className="block text-gray-700">Email Address</label>
              <input
                type="email"
                name="email"
                placeholder="Enter your Email Address"
                required
                className="w-full px-4 py-2 mt-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-400"
              />
            </div>

            {/* Show Username field only for signup */}
            {!isLogin && (
              <div className="mt-4">
                <label className="block text-gray-700">Username</label>
                <input
                  type="text"
                  name="username"
                  placeholder="Enter your Username"
                  className="w-full px-4 py-2 mt-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-400"
                />
              </div>
            )}

            <div className="mt-4">
              <label className="block text-gray-700">Password</label>
              <div className="relative">
                <input
                  type={showPassword ? "text" : "password"} // Conditional input type based on showPassword state
                  name="password"
                  placeholder="Enter your Password"
                  required
                  className="w-full px-4 py-2 mt-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-400"
                />
                <button
                  type="button"
                  onClick={togglePasswordVisibility}
                  className="absolute right-3 top-3 text-gray-500 hover:text-gray-700 focus:outline-none"
                >
                  {showPassword ? "Hide" : "Show"}
                </button>
              </div>
            </div>

            <button className="w-full mt-6 px-4 py-2 text-white bg-teal-500 rounded-lg hover:bg-teal-600">
              {isLogin ? "Login" : "Signup"}
            </button>

            <div className="mt-4 flex items-center justify-center">
              <button className="flex items-center px-4 py-2 border rounded-lg hover:bg-gray-100">
                <img
                  src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/512px-Google_%22G%22_Logo.svg.png"
                  alt="Google Logo"
                  className="w-5 h-5 mr-2"
                />
                {isLogin ? "Login" : "Signup"} with Google
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default TeacherLoginSignup;
