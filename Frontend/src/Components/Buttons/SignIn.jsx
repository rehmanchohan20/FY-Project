import { useState, useEffect } from "react";
import image from "../../assets/image5.jpg";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import { GOOGLE_AUTH_URL } from "../Auth/Contants/Constants.jsx"; // Import the Google Auth URL
import googleLogo from "../../assets/logo.svg"; // Import the Google logo

const EXPIRATION_HOURS = 6; // Set expiration to 6 hours

const SignIn = () => {
    const [isLogin, setIsLogin] = useState(true);
    const [formData, setFormData] = useState({ username: "", password: "", email: "" });
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");
    const [loading, setLoading] = useState(false);
    const [showPassword, setShowPassword] = useState(false);
    const [role, setRole] = useState("student"); // Default role
    const navigate = useNavigate();

    useEffect(() => {
        checkAndRetrieveUserData();
    }, []);

    const checkAndRetrieveUserData = () => {
        const storedData = JSON.parse(localStorage.getItem("userData"));
        if (storedData) {
            const currentTime = new Date().getTime();
            const timeDiff = currentTime - storedData.timestamp;
            const timeDiffHours = timeDiff / (1000 * 60 * 60);

            if (timeDiffHours < EXPIRATION_HOURS) {
                setFormData({ username: storedData.username, email: storedData.email });
                setRole(storedData.role);
            } else {
                localStorage.removeItem("userData");
            }
        }
    };

    const togglePasswordVisibility = () => setShowPassword(!showPassword);
    const toggleForm = () => {
        setIsLogin(!isLogin);
        setErrorMessage("");
        setSuccessMessage("");
        setFormData({ username: "", password: "", email: "" });
    };

    const handleChange = (e) => setFormData({ ...formData, [e.target.name]: e.target.value });

    const handleSubmit = async (event) => {
        event.preventDefault();
        setLoading(true);
        setErrorMessage("");

        const data = isLogin
            ? { username: formData.username, password: formData.password, role }
            : { ...formData, isTeacher: role === "teacher", role };

        const url = isLogin
            ? "http://localhost:8080/v1/auth/login"
            : "http://localhost:8080/v1/auth/registration";

        try {
            const response = await axios.post(url, data, { withCredentials: true });
            setSuccessMessage(response.data.message || "Success!");

            // Store user data in local storage with a timestamp
            const userData = {
                username: formData.username,
                email: formData.email,
                role,
                timestamp: new Date().getTime()
            };
            localStorage.setItem("userData", JSON.stringify(userData));

            if (isLogin) navigate("/dashboard");
            else navigate("/role-selection"); // Navigate to role selection on successful registration

        } catch (error) {
            console.error("Error during submission:", error);
            if (error.response) {
                setErrorMessage(
                    error.response.status === 500
                        ? error.response.data.message || "Email or username is already taken. Please choose another."
                        : "An error occurred. Please try again."
                );
            } else {
                setErrorMessage("An error occurred. Please check your network.");
            }
        } finally {
            setLoading(false);
            // Clear formData only after storing in local storage
            if (isLogin) setFormData({ username: "", password: "", email: "" });
            setTimeout(() => setSuccessMessage(""), 3000);
        }
    };

    const handleGoogleLogin = async () => {
        // Redirect to Google OAuth URL
        window.location.href = GOOGLE_AUTH_URL;
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
                                    <Link to="/resetpassword">Forgot Password?</Link>
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
                        <button className={`w-full mt-6 px-4 py-2 bg-teal-500 text-white rounded-lg ${loading ? "opacity-50 cursor-not-allowed" : ""}`} disabled={loading}>
                            {loading ? "Submitting..." : (isLogin ? "Login" : "Signup")}
                        </button>
                    </form>

                    <div className="social-login mt-6">
                        <a onClick={handleGoogleLogin} className="btn btn-block social-btn google flex items-center justify-center px-4 py-2 border rounded-lg border-gray-300 hover:bg-gray-200 transition">
                            <img src={googleLogo} alt="Google" className="w-5 h-5 mr-2" /> Continue with Google
                        </a>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SignIn;