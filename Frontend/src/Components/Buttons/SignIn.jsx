import { useState, useEffect } from "react";
import image from "../../assets/image5.jpg";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";

const SginIn = () => {
    const [isLogin, setIsLogin] = useState(true);
    const [formData, setFormData] = useState({ username: "", password: "", email: "" });
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");
    const [loading, setLoading] = useState(false);
    const [showPassword, setShowPassword] = useState(false);
    const [role, setRole] = useState("student");
    const navigate = useNavigate();

    const togglePasswordVisibility = () => setShowPassword(!showPassword);

    const toggleForm = () => {
        setIsLogin(!isLogin);
        setErrorMessage("");
        setSuccessMessage("");
        setFormData({ username: "", password: "", email: "" });
    };

    const handleChange = (e) => setFormData({ ...formData, [e.target.name]: e.target.value });

    useEffect(() => {
        const loadGoogleScript = () => {
            const script = document.createElement("script");
            script.src = "https://accounts.google.com/gsi/client";
            script.async = true;
            script.defer = true;
            script.onload = initializeGoogleSignIn;
            document.body.appendChild(script);
        };

        const initializeGoogleSignIn = () => {
            window.google.accounts.id.initialize({
                client_id: "491535410158-rs7qb1660bqlt7usj70ilj2o3ctu37em.apps.googleusercontent.com", // Use the correct client ID
                callback: handleGoogleResponse
            });
            window.google.accounts.id.renderButton(
                document.getElementById("googleSignInButton"),
                { theme: "outline", size: "large" }
            );
        };

        loadGoogleScript();

        return () => {
            const script = document.querySelector('script[src="https://accounts.google.com/gsi/client"]');
            if (script) script.remove();
        };
    }, []);

    const handleGoogleResponse = async (response) => {
        try {
            if (!response.credential) {
                throw new Error("Google Sign-In failed. No credential received.");
            }
            setLoading(true);

            // Send the token to your backend for verification
            const res = await axios.post(
                "http://localhost:8080/oauth2/callback/google",
                { token: response.credential },
                { headers: { "Content-Type": "application/json" }, withCredentials: true }
            );

            // Redirect after a successful response
            if (res.data && res.data.success) {
                setSuccessMessage("Successfully signed in with Google!");
                navigate("/role-selection"); // Redirect to role-selection page
            } else {
                setErrorMessage(res.data.message || "Google Sign-In failed. Please try again.");
            }
        } catch (error) {
            console.error("Error with Google Sign-In:", error);
            setErrorMessage("Google Sign-In failed. Please try again.");
        } finally {
            setLoading(false);
        }
    };

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
            if (isLogin) navigate("/dashboard");
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

                    <div className="flex justify-center mt-4">
                        <div id="googleSignInButton" className="w-full"></div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SginIn;
