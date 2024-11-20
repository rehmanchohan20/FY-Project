import React, { useEffect } from "react";
import { GOOGLE_AUTH_URL } from "../Auth/Contants/Constants.jsx";
import {useNavigate, useLocation, Navigate} from "react-router-dom";  // Use hooks from react-router-dom
import googleLogo from "../../assets/logo.svg";
import Alert from "react-s-alert";

const Login = ({ authenticated }) => {
  const navigate = useNavigate(); // Get navigate function
  const location = useLocation(); // Get location object

  useEffect(() => {
    // If the OAuth2 login encounters an error, the user is redirected to the /login page with an error.
    // Here we display the error and then remove the error query parameter from the location.
    if (location.state && location.state.error) {
      setTimeout(() => {
        Alert.error(location.state.error, {
          timeout: 5000,
        });
        navigate(location.pathname, { replace: true, state: {} }); // Navigate to the same path without error state
      }, 100);
    }
  }, [location, navigate]);

  if (authenticated) {
    return (
        <Navigate
            to={{
              pathname: "/",
              state: { from: location }, // Updated to use the location from the hook
            }}
        />
    );
  }

  return (
          <SampleLogin />
  );
};

const SampleLogin = () => {
  return (
      <div className="social-login">
        <a className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>
          <img src={googleLogo} alt="Google" /> Log in with Google
        </a>
      </div>
  );
};

export default Login;
