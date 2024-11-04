import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const RoleSelection = () => {
    const [role, setRole] = useState("student");
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const EXPIRATION_HOURS = 6; // Define expiration time in hours

    // Check if data in local storage is expired
    const checkAndRetrieveUserData = () => {
        const storedUsername = localStorage.getItem("username");
        const storedEmail = localStorage.getItem("email");
        const storedPassword = localStorage.getItem("password");
        const timestamp = localStorage.getItem("timestamp");

        if (storedUsername && storedEmail && timestamp) {
            const storedTime = new Date(parseInt(timestamp));
            const currentTime = new Date();
            const timeDifference = (currentTime - storedTime) / (1000 * 60 * 60); // in hours

            if (timeDifference < EXPIRATION_HOURS) {
                setUsername(storedUsername);
                setEmail(storedEmail);
                setPassword(storedPassword);
            } else {
                localStorage.removeItem("username");
                localStorage.removeItem("email");
                localStorage.removeItem("password");
                localStorage.removeItem("timestamp");
            }
        }
    };

    useEffect(() => {
        checkAndRetrieveUserData(); // Retrieve user data from local storage if not expired
    }, []);

    const handleRoleSubmit = async (event) => {
        event.preventDefault();

        // Ensure username and email are available
        if (!username || !email) {
            console.error("Missing required fields:", { username, email });
            return; // Prevent submission if required data is missing
        }

        const registrationData = {
            username,
            email,
            password, // Password should be handled securely, maybe set at initial login
            role,
            isTeacher: role === 'teacher'
        };

        // Retrieve the access token from local storage
        const accessToken = localStorage.getItem("accessToken");

        try {
            const response = await axios.post(
                "http://localhost:8080/v1/auth/registration",
                registrationData,
                {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${accessToken}`, // Add token to Authorization header
                    },
                    withCredentials: true,
                }
            );

            console.log(response.data);

            // Store token if available
            if (response.data.token) {
                localStorage.setItem("token", response.data.token); // Save the token
            }

            navigate("/dashboard"); // Redirect on successful registration
        } catch (error) {
            console.error("Error saving role:", error);
            // Optionally display an error message here
        }
    };

    return (
        <div className="flex min-h-screen items-center justify-center bg-gray-100">
            <div className="bg-white shadow-lg rounded-lg p-8">
                <h2 className="text-2xl mb-4">Select Your Role</h2>
                <form onSubmit={handleRoleSubmit}>
                    <label className="block mb-2">Enroll as:</label>
                    <select
                        value={role}
                        onChange={(e) => setRole(e.target.value)}
                        className="w-full px-4 py-2 mt-2 border rounded-lg"
                    >
                        <option value="student">Student</option>
                        <option value="teacher">Teacher</option>
                    </select>
                    <button
                        type="submit"
                        className="mt-4 w-full px-4 py-2 bg-teal-500 text-white rounded-lg"
                    >
                        Continue
                    </button>
                </form>
            </div>
        </div>
    );
};

export default RoleSelection;



// import { useState, useEffect } from "react";
// import { useNavigate } from "react-router-dom";
// import axios from "axios";
//
// const RoleSelection = () => {
//     const [role, setRole] = useState("student");
//     const [username, setUsername] = useState("");
//     const [email, setEmail] = useState("");
//     const [password, setPassword] = useState("");
//     const navigate = useNavigate();
//
//     useEffect(() => {
//         // Fetch values from local storage
//         const storedUsername = localStorage.getItem('username');
//         const storedEmail = localStorage.getItem('email');
//         const storedPassword = localStorage.getItem('password');
//
//         // Log the values retrieved from local storage for debugging
//         console.log("Retrieved from local storage:", { storedUsername, storedEmail, storedPassword });
//
//         // Update state with stored values
//         if (storedUsername) setUsername(storedUsername);
//         if (storedEmail) setEmail(storedEmail);
//         if (storedPassword) setPassword(storedPassword);
//     }, []);
//
//     const handleRoleSubmit = async (event) => {
//         event.preventDefault();
//
//         // Check if username and email are available
//         if (!username || !email) {
//             console.error("Missing required fields:", { username, email });
//             return; // Prevent submission if required data is missing
//         }
//
//         const registrationData = {
//             username,
//             email,
//             password, // Password should be handled securely and maybe set at the initial login
//             role,
//             isTeacher: role === 'teacher'
//         };
//
//         try {
//             const response = await axios.post("http://localhost:8080/v1/auth/registration", registrationData);
//             console.log(response.data);
//
//             // Optionally store user token in local storage if the response contains one
//             if (response.data.token) {
//                 localStorage.setItem('token', response.data.token); // Store the token
//             }
//
//             navigate("/dashboard"); // Redirect after successful registration
//         } catch (error) {
//             console.error("Error saving role:", error);
//             // Optionally display an error message to the user here
//         }
//     };
//
//     return (
//         <div className="flex min-h-screen items-center justify-center bg-gray-100">
//             <div className="bg-white shadow-lg rounded-lg p-8">
//                 <h2 className="text-2xl mb-4">Select Your Role</h2>
//                 <form onSubmit={handleRoleSubmit}>
//                     <label className="block mb-2">Enroll as:</label>
//                     <select
//                         value={role}
//                         onChange={(e) => setRole(e.target.value)}
//                         className="w-full px-4 py-2 mt-2 border rounded-lg"
//                     >
//                         <option value="student">Student</option>
//                         <option value="teacher">Teacher</option>
//                     </select>
//                     <button
//                         type="submit"
//                         className="mt-4 w-full px-4 py-2 bg-teal-500 text-white rounded-lg"
//                     >
//                         Continue
//                     </button>
//                 </form>
//             </div>
//         </div>
//     );
// };
//
// export default RoleSelection;
//
//
//
//
//
// // import { useState, useEffect } from "react";
// // import { useNavigate } from "react-router-dom";
// // import axios from "axios";
// //
// // const RoleSelection = () => {
// //     const [role, setRole] = useState("student");
// //     const [username, setUsername] = useState("");
// //     const [email, setEmail] = useState("");
// //     const [password, setPassword] = useState("");
// //     const navigate = useNavigate();
// //
// //     useEffect(() => {
// //         // Fetch values from local storage
// //         const storedUsername = localStorage.getItem("username");
// //         const storedEmail = localStorage.getItem("email");
// //         const storedPassword = localStorage.getItem("password");
// //
// //         console.log("Retrieved from local storage:", { storedUsername, storedEmail, storedPassword });
// //
// //         // Update state with stored values
// //         if (storedUsername) setUsername(storedUsername);
// //         if (storedEmail) setEmail(storedEmail);
// //         if (storedPassword) setPassword(storedPassword);
// //     }, []);
// //
// //     const handleRoleSubmit = async (event) => {
// //         event.preventDefault();
// //
// //         // Check if username, email, and password are available
// //         if (!username || !email || !password) {
// //             console.error("Missing required fields:", { username, email, password });
// //             return; // Prevent submission if required data is missing
// //         }
// //
// //         const registrationData = {
// //             username,
// //             email,
// //             password,
// //             role,
// //             isTeacher: role === "teacher",
// //         };
// //
// //         try {
// //             const response = await axios.post(
// //                 "http://localhost:8080/v1/auth/registration",
// //                 registrationData
// //             );
// //             console.log(response.data);
// //
// //             // Store role in local storage after successful registration
// //             localStorage.setItem("role", role);
// //
// //             navigate("/dashboard"); // Redirect after successful registration
// //         } catch (error) {
// //             console.error("Error saving role:", error);
// //         }
// //     };
// //
// //     return (
// //         <div className="flex min-h-screen items-center justify-center bg-gray-100">
// //             <div className="bg-white shadow-lg rounded-lg p-8">
// //                 <h2 className="text-2xl mb-4">Select Your Role</h2>
// //                 <form onSubmit={handleRoleSubmit}>
// //                     <label className="block mb-2">Enroll as:</label>
// //                     <select
// //                         value={role}
// //                         onChange={(e) => setRole(e.target.value)}
// //                         className="w-full px-4 py-2 mt-2 border rounded-lg"
// //                     >
// //                         <option value="student">Student</option>
// //                         <option value="teacher">Teacher</option>
// //                     </select>
// //                     <button
// //                         type="submit"
// //                         className="mt-4 w-full px-4 py-2 bg-teal-500 text-white rounded-lg"
// //                     >
// //                         Continue
// //                     </button>
// //                 </form>
// //             </div>
// //         </div>
// //     );
// // };
// //
// // export default RoleSelection;
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
// // import { useState, useEffect } from "react";
// // import { useNavigate } from "react-router-dom";
// // import axios from "axios";
// //
// // const RoleSelection = () => {
// //     const [role, setRole] = useState("student");
// //     const [username, setUsername] = useState("");
// //     const [email, setEmail] = useState("");
// //     const [password, setPassword] = useState("");
// //     const navigate = useNavigate();
// //
// //     useEffect(() => {
// //         // Fetch values from local storage
// //         const storedUsername = localStorage.getItem('username');
// //         const storedEmail = localStorage.getItem('email');
// //         const storedPassword = localStorage.getItem('password');
// //
// //         // Log the values retrieved from local storage for debugging
// //         console.log("Retrieved from local storage:", { storedUsername, storedEmail, storedPassword });
// //
// //         // Update state with stored values
// //         if (storedUsername) setUsername(storedUsername);
// //         if (storedEmail) setEmail(storedEmail);
// //         if (storedPassword) setPassword(storedPassword);
// //     }, []);
// //
// //
// //     const handleRoleSubmit = async (event) => {
// //         event.preventDefault();
// //
// //         // Check if username, email, and password are available
// //         if (!username || !email || !password) {
// //             console.error("Missing required fields:", { username, email, password });
// //             return; // Prevent submission if required data is missing
// //         }
// //
// //         const registrationData = {
// //             username,
// //             email,
// //             password,
// //             role,
// //             isTeacher: role === 'teacher'
// //         };
// //
// //         try {
// //             const response = await axios.post("http://localhost:8080/v1/auth/registration", registrationData);
// //             console.log(response.data);
// //             navigate("/dashboard"); // Redirect after successful registration
// //         } catch (error) {
// //             console.error("Error saving role:", error);
// //         }
// //     };
// //
// //     return (
// //         <div className="flex min-h-screen items-center justify-center bg-gray-100">
// //             <div className="bg-white shadow-lg rounded-lg p-8">
// //                 <h2 className="text-2xl mb-4">Select Your Role</h2>
// //                 <form onSubmit={handleRoleSubmit}>
// //                     <label className="block mb-2">Enroll as:</label>
// //                     <select
// //                         value={role}
// //                         onChange={(e) => setRole(e.target.value)}
// //                         className="w-full px-4 py-2 mt-2 border rounded-lg"
// //                     >
// //                         <option value="student">Student</option>
// //                         <option value="teacher">Teacher</option>
// //                     </select>
// //                     <button
// //                         type="submit"
// //                         className="mt-4 w-full px-4 py-2 bg-teal-500 text-white rounded-lg"
// //                     >
// //                         Continue
// //                     </button>
// //                 </form>
// //             </div>
// //         </div>
// //     );
// // };
// //
// // export default RoleSelection;
