// import React from 'react'
import { useState } from 'react'
import Navbar from './Components/Navbar/Navbar'
import Hero from './Components/Hero/Hero'
import Courses from './Pages/Courses/Courses'
import Guidance from './Pages/Guidance/Guidance'
import Footer from './Components/Footer/Footer'
import { Route,  Routes } from 'react-router-dom'
import LoginSignup from './Components/Buttons/SginIn'
import TeacherLoginSignup from './Components/Buttons/Teacher'
import LogOut from './Components/Buttons/LogOut'
import ResetEmail from "./Components/Buttons/ResetEmail.jsx";
// import { useState } from 'react'

// import SignInPopup from './Components/popFunction/SignInPopup'

const App = () => {
    // const [isAuthenticated, setIsAuthenticated] = useState(true);  // to check if user is authenticated or not

  // const user = localStorage.getItem("user");

  // const handleRegistrationSuccess = () => {
  //   setIsAuthenticated(true);  // Update the state to logged in
  // };
  //
  // // Handle user logout
  //   const handleLogin = () => {
  //       setIsAuthenticated(true);
  //   };


  return (
    <div>
        {/* Conditional Navbar rendering */}
        {/*{isAuthenticated ? <LogOut /> : <Navbar />}*/}
        <Navbar />
      <Routes>
      <Route exact path='/' element={<Hero/>}   />
      <Route  path='/courses' element={<Courses/>}/>
      <Route  path='/Guidance'  element={<Guidance/>}/>
      <Route  path='/signin'  element={<LoginSignup />}/>
      <Route  path='/teacher'  element={<TeacherLoginSignup/>}/>
      <Route path='/resetpassword' element={<ResetEmail/>}/>


      {/* make the sign in successFull  then  go  to  logout */}
      {/* <Route  path='/logout'  element={<LogOut/>}/> */}
      {/* <Route  path='/signin'  element={<SignInPopup/>}/> */}


     {/* <Routes>
      <Route exact path='/' element={<Hero/>}/>
      <Route  path='/courses' element={<Courses/>}/>
      <Route  path='/Guidance'  element={<Guidance/>}/>
      <Route  path='/signin'  element={<LoginSignup/>}/>
      <Route  path='/teacher'  element={<TeacherLoginSignup/>}/>
      <Route  path='/logout'  element={<LogOut/>}/> */}

      {/* make the sign in successFull  then  go  to  logout */}
      {/* <Route  path='/logout'  element={<LogOut/>}/> */}
      {/* <Route  path='/signin'  element={<SignInPopup/>}/> */}



     
     </Routes>
      <Footer/>

     
    </div>
  )
}

export default App
