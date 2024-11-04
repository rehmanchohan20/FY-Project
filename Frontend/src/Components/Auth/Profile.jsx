// src/components/Profile.js
import React, { Component } from 'react';
import { getCurrentUser } from '../Auth/utils/api.jsx'; // Ensure you create this API utility

class Profile extends Component {
    constructor(props) {
        super(props);

        this.state = {
            loading: true,
            currentUser: null,
        };
    }

    async componentDidMount() {
        await this.loadCurrentlyLoggedInUserDetails();
    }

    async loadCurrentlyLoggedInUserDetails() {
        try {
            const user = await getCurrentUser(); // Fetch user details from API
            this.setState({ currentUser: user, loading: false });
        } catch (error) {
            console.error("Error fetching user details:", error);
            this.setState({ loading: false });
        }
    }

    render() {
        const { loading, currentUser } = this.state;

        if (loading) {
            return <div>Loading...</div>; // Simple loading state
        }

        if (!currentUser) {
            return <div>No user data available.</div>; // Handle no user case
        }

        return (
            <div className="container">
                <h1>Profile</h1>
                <h2>{currentUser.name}</h2>
                <img src={currentUser.imageUrl} alt={currentUser.name} />
                {/* Add more user information here */}
            </div>
        );
    }
}

export default Profile;
