import { useState, useEffect } from "react";
import image from "../../assets/image5.jpg";
import axios from "axios";
import { useNavigate } from "react-router-dom"; // Update import to useNavigate

const LoginSignup = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [role, setRole] = useState("student");
  const [googleLoaded, setGoogleLoaded] = useState(false);
  const navigate = useNavigate(); // Initialize useNavigate

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const toggleForm = () => {
    setIsLogin(!isLogin);
    setErrorMessage("");
    setSuccessMessage("");
  };

  useEffect(() => {
    // Load Google API
    const loadGapi = () => {
      const script = document.createElement("script");
      script.src = "https://accounts.google.com/gsi/client";
      script.async = true;
      script.defer = true;
      script.onload = () => setGoogleLoaded(true);
      document.body.appendChild(script);
    };
    loadGapi();
    return () => {
      const script = document.querySelector('script[src="https://accounts.google.com/gsi/client"]');
      if (script) script.remove();
    };
  }, []);

  const handleGoogleSignIn = async () => {
    if (!googleLoaded || !window.google || !window.google.accounts) {
      setErrorMessage("Google Sign-In is not available yet. Please try again later.");
      return;
    }
    const googleAuth = window.google.accounts.oauth2.initTokenClient({
      client_id: "427082315752-vuiaobmnkqf68s6t3j79hku6g8joo7mb.apps.googleusercontent.com",
      scope: "email profile",
      callback: async (response) => {
        try {
          const res = await axios.get("http://localhost:8080/dashboard", {
            token: response.access_token,
          });
          console.log("Server response:", res.data); // Log server response for debugging
          setErrorMessage("");
          setSuccessMessage("Successfully signed in with Google!");
          navigate("/"); // Redirect to dashboard using React Router
        } catch (error) {
          console.error("Error with Google Sign-In:", error.response?.data || error.message);
          setErrorMessage("Google Sign-In failed. Please try again.");
        }
      },
    });
    googleAuth.requestAccessToken();
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    const form = new FormData(event.target);
    const data = {
      username: form.get("username"),
      password: form.get("password"),
    };

    if (!isLogin) {
      // Ensure email and other fields are included for registration
      data.email = form.get("email");
      data.isTeacher = role === "teacher"; // Convert role to boolean
      data.role = role; // Make sure role is correctly set
    }

    const url = isLogin
        ? "http://localhost:8080/v1/auth/login"
        : "http://localhost:8080/v1/auth/registration";

    try {
      const response = await axios.post(url, data);
      setErrorMessage("");
      setSuccessMessage(response.data.message || "Success!");
      if (isLogin) {
        navigate("/dashboard"); // Redirect to dashboard using React Router
      }
    } catch (error) {
      console.error("Error during submission:", error);
      // Make sure to access error response correctly
      setErrorMessage(error.response?.data?.message || "An error occurred. Please try again.");
    } finally {
      setLoading(false);
      event.target.reset();
      setTimeout(() => {
        setSuccessMessage("");
      }, 3000);
    }
  };

  return (
      <div className="flex min-h-screen items-center justify-center bg-gray-100">
        <div className="flex max-w-4xl w-full bg-white shadow-lg rounded-lg overflow-hidden">
          <div
              className="w-1/2 bg-cover flex items-center justify-center"
              style={{ backgroundImage: `url(${image})` }}
          >
            <div className="flex flex-col justify-center items-center h-full p-8">
              <h2 className="text-white text-3xl font-bold mb-4">
                Start your career with professionals
              </h2>
            </div>
          </div>

          <div className="w-1/2 p-8">
            <div className="flex justify-center my-6">
              <button
                  onClick={toggleForm}
                  className={`px-4 py-2 rounded ${isLogin ? "bg-teal-500 text-white" : "bg-teal-100 text-teal-700 hover:bg-teal-200"}`}
              >
                Login
              </button>
              <button
                  onClick={toggleForm}
                  className={`px-4 py-2 rounded ${!isLogin ? "bg-teal-500 text-white" : "bg-teal-100 text-teal-700 hover:bg-teal-200"}`}
              >
                Register
              </button>
            </div>

            <form className="mt-6" onSubmit={handleSubmit}>
              {!isLogin && (
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
              )}
              <div className="mt-4">
                <label className="block text-gray-700">Username</label>
                <input
                    type="text"
                    name="username"
                    placeholder="Enter your Username"
                    required
                    className="w-full px-4 py-2 mt-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-400"
                />
              </div>
              <div className="mt-4">
                <label className="block text-gray-700">Password</label>
                <div className="relative">
                  <input
                      type={showPassword ? "text" : "password"}
                      name="password"
                      placeholder="Enter your Password"
                      autoComplete="current-password"
                      required
                      className="w-full px-4 py-2 mt-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-400"
                  />
                  <button
                      type="button"
                      onClick={togglePasswordVisibility}
                      className="absolute inset-y-0 right-0 px-3 py-2 text-gray-600"
                  >
                    {showPassword ? "Hide" : "Show"}
                  </button>
                </div>
              </div>
              {!isLogin && (
                  <div className="mt-4">
                    <label className="block text-gray-700">Enroll as</label>
                    <select
                        value={role}
                        onChange={(e) => setRole(e.target.value)}
                        className="w-full px-4 py-2 mt-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-400"
                    >
                      <option value="student">Student</option>
                      <option value="teacher">Teacher</option>
                    </select>
                  </div>
              )}
              {errorMessage && <p className="mt-4 text-red-600 text-center">{errorMessage}</p>}
              {successMessage && <p className="mt-4 text-green-600 text-center">{successMessage}</p>}
              <button
                  className={`w-full mt-6 px-4 py-2 text-white bg-teal-500 rounded-lg hover:bg-teal-600 ${loading ? "opacity-50 cursor-not-allowed" : ""}`}
                  disabled={loading}
              >
                {loading ? "Submitting..." : (isLogin ? "Login" : "Signup")}
              </button>
            </form>

            <div className="flex justify-center mt-4">
              <button
                  onClick={handleGoogleSignIn}
                  className="px-4 py-2 text-white bg-red-500 rounded-lg hover:bg-red-600"
              >
                {loading ? "Loading..." : "Continue with Google"}
              </button>
            </div>
          </div>
        </div>
      </div>
  );
};

export default LoginSignup;
