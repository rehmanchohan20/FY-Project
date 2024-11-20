import React from 'react';
import Navbar from './Components/Navbar/Navbar';
import Hero from './Components/Hero/Hero';
import Courses from './Pages/Courses/Courses';
import Guidance from './Pages/Guidance/Guidance';
import Footer from './Components/Footer/Footer';
import { Navigate, Routes, Route } from 'react-router-dom'; // Fixed imports
import LoginSignup from './Components/Buttons/SignIn.jsx';
import TeacherLoginSignup from './Components/Buttons/Teacher';
import ResetEmail from "./Components/Buttons/ResetEmail.jsx";
import RoleSelection from "./Components/Rolle/RoleSelection.jsx";
import StudentPanel from "./Pages/Student Dashboard/StudentPanel.jsx";
import TeacherPanel from "./Pages/Teacher Dashboard/TeacherPanel.jsx";
import TestCorsRequest from "./Pages/TestCorsRequest.jsx";
import CreateCourse from "./Pages/Teacher Dashboard/CreateCourse.jsx";
import OAuth2Callback from "./Components/Auth/OAuth2Callback.jsx";
import Login from "./Components/Auth/Login.jsx";
import OAuth2RedirectHandler from "./Components/Auth/OAuth2RedirectHandler/OAuth2RedirectHandler.jsx";
import Profile from "./Components/Auth/Profile.jsx";
// import SessionManager from "./Components/Auth/SessionManager/SessionManager.jsx";
import Payment from "./Pages/Payment.jsx";
import ConfirmPayment from "./Pages/confirmPayment.jsx";

const App = () => {
    const authenticated = false;

    return (
        <div>
            <Navbar />
            <Routes>
                <Route exact path='/' element={<Hero />} />
                <Route path='/courses' element={<Courses />} />
                <Route path='/Guidance' element={<Guidance />} />
                <Route path='/signin' element={<LoginSignup />} />
                <Route path='/teacher' element={<TeacherLoginSignup />} />
                <Route path='/resetpassword' element={<ResetEmail />} />
                <Route path="/role-selection" element={<RoleSelection />} />
                <Route path="/teacher-panel" element={<TeacherPanel />} />
                <Route path="/student-panel" element={<StudentPanel />} />
                <Route path="/test" element={<TestCorsRequest />} />
                <Route path="/CreateCourse" element={<CreateCourse />} /> {/* Fixed spelling */}
                <Route path="/oauth2/callback/google" element={<OAuth2Callback />} />
                <Route path="/login" element={<Login />} />
                <Route path="/oauth2/callback" element={<OAuth2RedirectHandler />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/payment" element={<Payment/>}/>
                <Route path="/confirmPayment" element={<ConfirmPayment/>}/>
                <Route path="*" element={<Navigate to="/" replace />} /> {/* Redirect to home instead of dashboard */}
            </Routes>
            <Footer />
        </div>
    );
}

export default App;
