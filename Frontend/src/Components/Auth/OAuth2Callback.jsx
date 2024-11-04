import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const OAuth2Callback = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const handleCallback = async () => {
            const params = new URLSearchParams(window.location.search);
            const token = params.get("token");

            if (token) {
                localStorage.setItem("accessToken", token);

                try {
                    const response = await axios.post(
                        "http://localhost:8080/oauth2/callback/google",
                        { token },
                        { headers: { "Content-Type": "application/json" }, withCredentials: true }
                    );

                    if (response.data && response.data.success) {
                        const { username, email } = response.data.user;

                        // Store user data including role
                        const userData = {
                            username,
                            email,
                            role: response.data.user.role || "student", // Set default role if not provided
                            timestamp: new Date().getTime()
                        };
                        localStorage.setItem("userData", JSON.stringify(userData));

                        navigate("/dashboard"); // Redirect to the dashboard after successful login
                    } else {
                        console.error("Login error:", response.data.message);
                        alert("Login failed: " + response.data.message);
                        navigate("/");
                    }
                } catch (error) {
                    console.error("Error during OAuth2 callback:", error);
                    alert("An error occurred. Please try again.");
                    navigate("/");
                }
            } else {
                alert("No token received. Please try to sign in again.");
                navigate("/");
            }
        };

        handleCallback();
    }, [navigate]);

    return (
        <div className="flex items-center justify-center min-h-screen">
            <h1>Processing...</h1>
        </div>
    );
};

export default OAuth2Callback;


// import { useEffect } from "react";
// import { useNavigate } from "react-router-dom";
// import axios from "axios";
//
// const OAuth2Callback = () => {
//     const navigate = useNavigate();
//
//     useEffect(() => {
//         const handleCallback = async () => {
//             const params = new URLSearchParams(window.location.search);
//             const token = params.get("token"); // Assuming your backend sends the token as a URL parameter
//
//             if (token) {
//                 // Store the token in local storage
//                 localStorage.setItem("accessToken", token);
//
//                 try {
//                     const response = await axios.post(
//                         "http://localhost:8080/oauth2/callback/google",
//                         { token },
//                         { headers: { "Content-Type": "application/json" }, withCredentials: true }
//                     );
//
//                     console.log("Response from backend:", response.data);
//
//                     if (response.data && response.data.success) {
//                         // Extract user data (make sure these keys match your backend response)
//                         const { username, email } = response.data.user;
//
//                         // Debugging: Log user data before storing
//                         console.log("User data to be stored:", { username, email });
//
//                         // Store user data in local storage
//                         localStorage.setItem("username", username);
//                         localStorage.setItem("email", email);
//                         localStorage.setItem("password", ""); // Optional, based on your requirements
//
//                         // Confirm that data was stored
//                         console.log("Local Storage after setting:", {
//                             username: localStorage.getItem("username"),
//                             email: localStorage.getItem("email"),
//                             password: localStorage.getItem("password"),
//                         });
//
//                         navigate("/role-selection"); // Redirect to the role selection page
//                     } else {
//                         console.error("Login error:", response.data.message);
//                         alert("Login failed: " + response.data.message);
//                         navigate("/"); // Redirect to home or login page
//                     }
//                 } catch (error) {
//                     console.error("Error during OAuth2 callback:", error);
//                     alert("An error occurred. Please try again.");
//                     navigate("/"); // Redirect to home or login page
//                 }
//             } else {
//                 alert("No token received. Please try to sign in again.");
//                 navigate("/"); // Redirect to home or login page
//             }
//         };
//
//         handleCallback();
//     }, [navigate]);
//
//     return (
//         <div className="flex items-center justify-center min-h-screen">
//             <h1>Processing...</h1>
//         </div>
//     );
// };
//
// export default OAuth2Callback;








// import { useEffect } from "react";
// import { useNavigate } from "react-router-dom";
// import axios from "axios";
//
// const OAuth2Callback = () => {
//     const navigate = useNavigate();
//
//     useEffect(() => {
//         const handleCallback = async () => {
//             const params = new URLSearchParams(window.location.search);
//             const token = params.get("token"); // Assuming your backend sends the token as a URL parameter
//
//             if (token) {
//                 try {
//                     const response = await axios.post(
//                         "http://localhost:8080/oauth2/callback/google",
//                         { token },
//                         { headers: { "Content-Type": "application/json" }, withCredentials: true }
//                     );
//
//                     console.log("Response from backend:", response.data);
//
//                     if (response.data && response.data.success) {
//                         // Extract user data (make sure these keys match your backend response)
//                         const { username, email } = response.data.user;
//
//                         // Debugging: Log user data before storing
//                         console.log("User data to be stored:", { username, email });
//
//                         // Store user data in local storage
//                         localStorage.setItem("username", username);
//                         localStorage.setItem("email", email);
//                         localStorage.setItem("password", ""); // This might be removed based on your requirements
//
//                         // Confirm that data was stored
//                         console.log("Local Storage after setting:", {
//                             username: localStorage.getItem("username"),
//                             email: localStorage.getItem("email"),
//                             password: localStorage.getItem("password"),
//                         });
//
//                         navigate("/role-selection"); // Redirect to the role selection page
//                     } else {
//                         console.error("Login error:", response.data.message);
//                         alert("Login failed: " + response.data.message);
//                         navigate("/"); // Redirect to home or login page
//                     }
//                 } catch (error) {
//                     console.error("Error during OAuth2 callback:", error);
//                     alert("An error occurred. Please try again.");
//                     navigate("/"); // Redirect to home or login page
//                 }
//             } else {
//                 alert("No token received. Please try to sign in again.");
//                 navigate("/"); // Redirect to home or login page
//             }
//         };
//
//         handleCallback();
//     }, [navigate]);
//
//     return (
//         <div className="flex items-center justify-center min-h-screen">
//             <h1>Processing...</h1>
//         </div>
//     );
// };
//
// export default OAuth2Callback;



// import { useEffect } from "react";
// import { useNavigate } from "react-router-dom";
// import axios from "axios";
//
// const OAuth2Callback = () => {
//     const navigate = useNavigate();
//
//     useEffect(() => {
//         const handleCallback = async () => {
//             const params = new URLSearchParams(window.location.search);
//             const token = params.get("token"); // Assuming your backend sends the token as a URL parameter
//
//             if (token) {
//                 try {
//                     // Send the token to your backend for verification and sign in
//                     const response = await axios.post(
//                         "http://localhost:8080/oauth2/callback/google",
//                         { token },
//                         { headers: { "Content-Type": "application/json" }, withCredentials: true }
//                     );
//
//                     // Debugging: Log the response from your backend
//                     console.log("Response from backend:", response.data);
//
//                     // Handle the response from your backend
//                     if (response.data && response.data.success) {
//                         // Extract user data (ensure these keys match your backend response)
//                         const { username, email } = response.data.user;
//
//                         // Debugging: Log user data
//                         console.log("User data received:", { username, email });
//
//                         // Store user data in local storage
//                         localStorage.setItem("username", username);
//                         localStorage.setItem("email", email);
//                         localStorage.setItem("password", ""); // Only if you need to save this for some reason
//
//                         // Navigate to role selection page
//                         navigate("/role-selection"); // Redirect to the role selection page
//                     } else {
//                         // Handle error case
//                         console.error("Login error:", response.data.message);
//                         alert("Login failed: " + response.data.message);
//                         navigate("/"); // Redirect to home or login page
//                     }
//                 } catch (error) {
//                     console.error("Error during OAuth2 callback:", error);
//                     alert("An error occurred. Please try again.");
//                     navigate("/"); // Redirect to home or login page
//                 }
//             } else {
//                 alert("No token received. Please try to sign in again.");
//                 navigate("/"); // Redirect to home or login page
//             }
//         };
//
//         handleCallback();
//     }, [navigate]);
//
//     return (
//         <div className="flex items-center justify-center min-h-screen">
//             <h1>Processing...</h1>
//         </div>
//     );
// };
//
// export default OAuth2Callback;



// import { useEffect } from "react";
// import { useNavigate } from "react-router-dom";
// import axios from "axios";
//
// const OAuth2Callback = () => {
//     const navigate = useNavigate();
//
//     useEffect(() => {
//         const handleCallback = async () => {
//             const params = new URLSearchParams(window.location.search);
//             const token = params.get("token");
//
//             if (token) {
//                 try {
//                     const response = await axios.post(
//                         "http://localhost:8080/oauth2/callback/google",
//                         { token },
//                         { headers: { "Content-Type": "application/json" }, withCredentials: true }
//                     );
//
//                     if (response.data && response.data.success) {
//                         // Save the necessary user information in local storage
//                         const { username, email, password } = response.data.user; // Adjust keys as per your response
//                         localStorage.setItem("username", username);
//                         localStorage.setItem("email", email);
//                         localStorage.setItem("password", password); // if needed
//
//                         navigate("/role-selection");
//                     } else {
//                         console.error(response.data.message);
//                         alert("Login failed: " + response.data.message);
//                         navigate("/login");
//                     }
//                 } catch (error) {
//                     console.error("Error during OAuth2 callback:", error);
//                     alert("An error occurred. Please try again.");
//                     navigate("/login");
//                 }
//             } else {
//                 alert("No token received. Please try to sign in again.");
//                 navigate("/login");
//             }
//         };
//
//         handleCallback();
//     }, [navigate]);
//
//     return (
//         <div className="flex items-center justify-center min-h-screen">
//             <h1>Processing...</h1>
//         </div>
//     );
// };
//
// export default OAuth2Callback;




// import { useEffect } from "react";
// import { useNavigate } from "react-router-dom";
// import axios from "axios";
//
// const OAuth2Callback = () => {
//     const navigate = useNavigate();
//
//     useEffect(() => {
//         const handleCallback = async () => {
//             const params = new URLSearchParams(window.location.search);
//             const token = params.get("token");
//
//             if (token) {
//                 try {
//                     // Verify token with backend
//                     const response = await axios.post(
//                         "http://localhost:8080/oauth2/callback/google",
//                         { token },
//                         { headers: { "Content-Type": "application/json" }, withCredentials: true }
//                     );
//
//                     if (response.data && response.data.success) {
//                         // Optionally store the token in local storage if needed
//                         localStorage.setItem("token", token);
//                         navigate("/role-selection");
//                     } else {
//                         console.error(response.data.message);
//                         alert("Login failed: " + response.data.message);
//                         navigate("/"); // Redirect to login or home page
//                     }
//                 } catch (error) {
//                     console.error("Error during OAuth2 callback:", error);
//                     alert("An error occurred. Please try again.");
//                     navigate("/"); // Redirect to login or home page
//                 }
//             } else {
//                 alert("No token received. Please try to sign in again.");
//                 navigate("/"); // Redirect to login or home page
//             }
//         };
//
//         handleCallback();
//     }, [navigate]);
//
//     return (
//         <div className="flex items-center justify-center min-h-screen">
//             <h1>Processing...</h1>
//         </div>
//     );
// };
//
// export default OAuth2Callback;








// import { useEffect } from "react";
// import { useNavigate } from "react-router-dom";
// import axios from "axios";
//
// const OAuth2Callback = () => {
//     const navigate = useNavigate();
//
//     useEffect(() => {
//         const handleCallback = async () => {
//             const params = new URLSearchParams(window.location.search);
//             const token = params.get("token"); // Assuming your backend sends the token as a URL parameter
//
//             if (token) {
//                 try {
//                     // Send the token to your backend for verification and sign in
//                     const response = await axios.post(
//                         "http://localhost:8080/oauth2/callback/google",
//                         { token },
//                         { headers: { "Content-Type": "application/json" }, withCredentials: true }
//                     );
//
//                     // Handle the response from your backend
//                     if (response.data && response.data.success) {
//                         // Successful login
//                         navigate("/role-selection"); // Redirect to the role selection page
//                     } else {
//                         // Handle error case
//                         console.error(response.data.message);
//                         alert("Login.jsx failed: " + response.data.message);
//                         navigate("/"); // Redirect to home or login page
//                     }
//                 } catch (error) {
//                     console.error("Error during OAuth2 callback:", error);
//                     alert("An error occurred. Please try again.");
//                     navigate("/"); // Redirect to home or login page
//                 }
//             } else {
//                 alert("No token received. Please try to sign in again.");
//                 navigate("/"); // Redirect to home or login page
//             }
//         };
//
//         handleCallback();
//     }, [navigate]);
//
//     return (
//         <div className="flex items-center justify-center min-h-screen">
//             <h1>Processing...</h1>
//         </div>
//     );
// };
//
// export default OAuth2Callback;
