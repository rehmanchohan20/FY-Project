// src/components/OAuth2RedirectHandler.js
import React, { Component } from 'react';
import {ACCESS_TOKEN} from "../Contants/Constants.jsx";
import { setToken } from '../utils/auth';
import { Navigate } from 'react-router-dom';

class OAuth2RedirectHandler extends Component {
    getUrlParameter(name) {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
        const results = regex.exec(this.props.location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    }

    render() {
        const token = this.getUrlParameter('token');
        const error = this.getUrlParameter('error');

        if (token) {
            setToken(token); // Set token in a cookie
            return <Navigate to={{ pathname: '/profile', state: { from: this.props.location } }} />;
        } else {
            return <Navigate to={{ pathname: '/login', state: { from: this.props.location, error: error } }} />;
        }
    }
}

export default OAuth2RedirectHandler;
