import { useState, useEffect } from "react";
import image from "../../assets/image5.jpg";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom"; // Import Link

const LoginSignup = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({ username: "", password: "", email: "" });
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [role, setRole] = useState("student");
  const [googleLoaded, setGoogleLoaded] = useState(false);
  const navigate = useNavigate();

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const toggleForm = () => {
    setIsLogin(!isLogin);
    setErrorMessage("");
    setSuccessMessage("");
    setFormData({ username: "", password: "", email: "" });
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  useEffect(() => {
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
          if (!response.access_token) {
            throw new Error("No access token received from Google.");
          }

          const res = await axios.get("http://localhost:8080/dashboard", {
            token: response.access_token,
          });

          setSuccessMessage("Successfully signed in with Google!");
          navigate("/dashboard");
        } catch (error) {
          console.error("Error with Google Sign-In:", error);
          setErrorMessage("Google Sign-In failed. Please try again. " + (error.response ? error.response.data.message : error.message));
        }
      },
    });

    googleAuth.requestAccessToken();
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setErrorMessage("");

    const data = { ...formData, isTeacher: role === "teacher", role };
    const url = isLogin
        ? "http://localhost:8080/v1/auth/login"
        : "http://localhost:8080/v1/auth/registration";

    try {
      const response = await axios.post(url, data);
      setSuccessMessage(response.data.message || "Success!");
      if (isLogin) {
        navigate("/dashboard");
      }
    } catch (error) {
      console.error("Error during submission:", error);
      if (error.response && error.response.status === 500) { // Assuming 409 is returned for conflicts (email/username taken)
        const errorMessage = error.response.data.message || "Email or username is already taken. Please choose another.";
        setErrorMessage(errorMessage);
      } else {
        setErrorMessage("An error occurred. Please try again.");
      }
    } finally {
      setLoading(false);
      setFormData({ username: "", password: "", email: "" });
      setTimeout(() => setSuccessMessage(""), 3000);
    }
  };

  return (
      <div className="flex min-h-screen items-center justify-center bg-gray-100">
        <div className="flex max-w-4xl w-full bg-white shadow-lg rounded-lg overflow-hidden">
          <div className="w-1/2 bg-cover" style={{ backgroundImage: `url(${image})` }}></div>

          <div className="w-1/2 p-8">
            <div className="flex justify-center mb-6">
              <button onClick={toggleForm} className={`px-4 py-2 rounded ${isLogin ? "bg-teal-500 text-white" : "bg-teal-100 text-teal-700"}`}>Login</button>
              <button onClick={toggleForm} className={`px-4 py-2 rounded ${!isLogin ? "bg-teal-500 text-white" : "bg-teal-100 text-teal-700"}`}>Register</button>
            </div>

            <form className="mt-6" onSubmit={handleSubmit}>
              {!isLogin && (
                  <div>
                    <label>Email</label>
                    <input type="email" name="email" value={formData.email} onChange={handleChange} required className="w-full px-4 py-2 mt-2 border rounded-lg" />
                  </div>
              )}
              <div className="mt-4">
                <label>Username</label>
                <input type="text" name="username" value={formData.username} onChange={handleChange} required className="w-full px-4 py-2 mt-2 border rounded-lg" />
              </div>
              <div className="mt-4">
                <label>Password</label>
                <div className="relative">
                  <input type={showPassword ? "text" : "password"} name="password" value={formData.password} onChange={handleChange} required className="w-full px-4 py-2 mt-2 border rounded-lg" />
                  <button type="button" onClick={togglePasswordVisibility} className="absolute inset-y-0 right-0 px-3 py-2">{showPassword ? "Hide" : "Show"}</button>
                </div>
                {isLogin && (
                    <div className="mt-2 text-sm text-teal-500 text-right">
                      <Link to="/resetpassword">Forgot Password?</Link> {/* Add link here */}
                    </div>
                )}
              </div>
              {!isLogin && (
                  <div className="mt-4">
                    <label>Enroll as</label>
                    <select value={role} onChange={(e) => setRole(e.target.value)} className="w-full px-4 py-2 mt-2 border rounded-lg">
                      <option value="student">Student</option>
                      <option value="teacher">Teacher</option>
                    </select>
                  </div>
              )}
              {errorMessage && <p className="mt-4 text-red-600 text-center">{errorMessage}</p>}
              {successMessage && <p className="mt-4 text-green-600 text-center">{successMessage}</p>}
              <button className={`w-full mt-6 px-4 py-2 bg-teal-500 text-white rounded-lg ${loading ? "opacity-50 cursor-not-allowed" : ""}`} disabled={loading}>{loading ? "Submitting..." : (isLogin ? "Login" : "Signup")}</button>
            </form>

            <div className="flex justify-center mt-4">
              <button onClick={handleGoogleSignIn} className="px-4 py-2 bg-red-500 text-white rounded-lg">{loading ? "Loading..." : "Continue with Google"}</button>
            </div>
          </div>
        </div>
      </div>
  );
};

export default LoginSignup;




























// import { useState, useEffect } from "react";
// import image from "../../assets/image5.jpg";
// import axios from "axios";
// import { useNavigate } from "react-router-dom";
//
// const LoginSignup = () => {
//   const [isLogin, setIsLogin] = useState(true);
//   const [formData, setFormData] = useState({ username: "", password: "", email: "" });
//   const [errorMessage, setErrorMessage] = useState("");
//   const [successMessage, setSuccessMessage] = useState("");
//   const [loading, setLoading] = useState(false);
//   const [showPassword, setShowPassword] = useState(false);
//   const [role, setRole] = useState("student");
//   const [googleLoaded, setGoogleLoaded] = useState(false);
//   const navigate = useNavigate();
//
//   const togglePasswordVisibility = () => {
//     setShowPassword(!showPassword);
//   };
//
//   const toggleForm = () => {
//     setIsLogin(!isLogin);
//     setErrorMessage("");
//     setSuccessMessage("");
//     setFormData({ username: "", password: "", email: "" });
//   };
//
//   const handleChange = (e) => {
//     setFormData({ ...formData, [e.target.name]: e.target.value });
//   };
//
//   useEffect(() => {
//     const loadGapi = () => {
//       const script = document.createElement("script");
//       script.src = "https://accounts.google.com/gsi/client";
//       script.async = true;
//       script.defer = true;
//       script.onload = () => setGoogleLoaded(true);
//       document.body.appendChild(script);
//     };
//
//     loadGapi();
//
//     return () => {
//       const script = document.querySelector('script[src="https://accounts.google.com/gsi/client"]');
//       if (script) script.remove();
//     };
//   }, []);
//
//   const handleGoogleSignIn = async () => {
//     if (!googleLoaded || !window.google || !window.google.accounts) {
//       setErrorMessage("Google Sign-In is not available yet. Please try again later.");
//       return;
//     }
//
//     const googleAuth = window.google.accounts.oauth2.initTokenClient({
//       client_id: "427082315752-vuiaobmnkqf68s6t3j79hku6g8joo7mb.apps.googleusercontent.com",
//       scope: "email profile",
//       callback: async (response) => {
//         try {
//           if (!response.access_token) {
//             throw new Error("No access token received from Google.");
//           }
//
//           const res = await axios.get("http://localhost:8080/dashboard", {
//             token: response.access_token,
//           });
//
//           setSuccessMessage("Successfully signed in with Google!");
//           navigate("/dashboard");
//         } catch (error) {
//           console.error("Error with Google Sign-In:", error);
//           setErrorMessage("Google Sign-In failed. Please try again. " + (error.response ? error.response.data.message : error.message));
//         }
//       },
//     });
//
//     googleAuth.requestAccessToken();
//   };
//
//   const handleSubmit = async (event) => {
//     event.preventDefault();
//     setLoading(true);
//     setErrorMessage("");
//
//     const data = { ...formData, isTeacher: role === "teacher", role };
//     const url = isLogin
//         ? "http://localhost:8080/v1/auth/login"
//         : "http://localhost:8080/v1/auth/registration";
//
//     try {
//       const response = await axios.post(url, data);
//       setSuccessMessage(response.data.message || "Success!");
//       if (isLogin) {
//         navigate("/dashboard");
//       }
//     } catch (error) {
//       console.error("Error during submission:", error);
//       if (error.response && error.response.status === 500) { // Assuming 409 is returned for conflicts (email/username taken)
//         const errorMessage = error.response.data.message || "Email or username is already taken. Please choose another.";
//         setErrorMessage(errorMessage);
//       } else {
//         setErrorMessage("An error occurred. Please try again.");
//       }
//     } finally {
//       setLoading(false);
//       setFormData({ username: "", password: "", email: "" });
//       setTimeout(() => setSuccessMessage(""), 3000);
//     }
//   };
//
//   return (
//       <div className="flex min-h-screen items-center justify-center bg-gray-100">
//         <div className="flex max-w-4xl w-full bg-white shadow-lg rounded-lg overflow-hidden">
//           <div className="w-1/2 bg-cover" style={{ backgroundImage: `url(${image})` }}></div>
//
//           <div className="w-1/2 p-8">
//             <div className="flex justify-center mb-6">
//               <button onClick={toggleForm} className={`px-4 py-2 rounded ${isLogin ? "bg-teal-500 text-white" : "bg-teal-100 text-teal-700"}`}>Login</button>
//               <button onClick={toggleForm} className={`px-4 py-2 rounded ${!isLogin ? "bg-teal-500 text-white" : "bg-teal-100 text-teal-700"}`}>Register</button>
//             </div>
//
//             <form className="mt-6" onSubmit={handleSubmit}>
//               {!isLogin && (
//                   <div>
//                     <label>Email</label>
//                     <input type="email" name="email" value={formData.email} onChange={handleChange} required className="w-full px-4 py-2 mt-2 border rounded-lg" />
//                   </div>
//               )}
//               <div className="mt-4">
//                 <label>Username</label>
//                 <input type="text" name="username" value={formData.username} onChange={handleChange} required className="w-full px-4 py-2 mt-2 border rounded-lg" />
//               </div>
//               <div className="mt-4">
//                 <label>Password</label>
//                 <div className="relative">
//                   <input type={showPassword ? "text" : "password"} name="password" value={formData.password} onChange={handleChange} required className="w-full px-4 py-2 mt-2 border rounded-lg" />
//                   <button type="button" onClick={togglePasswordVisibility} className="absolute inset-y-0 right-0 px-3 py-2">{showPassword ? "Hide" : "Show"}</button>
//                 </div>
//               </div>
//               {!isLogin && (
//                   <div className="mt-4">
//                     <label>Enroll as</label>
//                     <select value={role} onChange={(e) => setRole(e.target.value)} className="w-full px-4 py-2 mt-2 border rounded-lg">
//                       <option value="student">Student</option>
//                       <option value="teacher">Teacher</option>
//                     </select>
//                   </div>
//               )}
//               {errorMessage && <p className="mt-4 text-red-600 text-center">{errorMessage}</p>}
//               {successMessage && <p className="mt-4 text-green-600 text-center">{successMessage}</p>}
//               <button className={`w-full mt-6 px-4 py-2 bg-teal-500 text-white rounded-lg ${loading ? "opacity-50 cursor-not-allowed" : ""}`} disabled={loading}>{loading ? "Submitting..." : (isLogin ? "Login" : "Signup")}</button>
//             </form>
//
//             <div className="flex justify-center mt-4">
//               <button onClick={handleGoogleSignIn} className="px-4 py-2 bg-red-500 text-white rounded-lg">{loading ? "Loading..." : "Continue with Google"}</button>
//             </div>
//           </div>
//         </div>
//       </div>
//   );
// };
//
// export default LoginSignup;
