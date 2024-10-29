import React, { useState } from 'react';
import axios from 'axios';

const ResetEmail = () => {
    const [email, setEmail] = useState('');
    const [otp, setOtp] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [loading, setLoading] = useState(false);

    const [isOtpFieldVisible, setIsOtpFieldVisible] = useState(false);
    const [isNewPasswordFieldVisible, setIsNewPasswordFieldVisible] = useState(false);

    // Step 1: Send Reset Email
    const handleSendEmail = async (event) => {
        event.preventDefault();
        setLoading(true);
        setErrorMessage('');
        setSuccessMessage('');

        try {
            const response = await axios.post(
                'http://localhost:8080/v1/auth/forgot',
                null,
                { params: { email } }
            );
            setSuccessMessage(response.data.message || 'OTP has been sent to your email.');
            setIsOtpFieldVisible(true); // Unlock OTP field if email is successfully sent
        } catch (error) {
            console.error('Error sending reset email:', error);
            setErrorMessage(error.response?.data?.message || 'Failed to send reset email. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    // Step 2: Verify OTP
    const handleVerifyOtp = async (event) => {
        event.preventDefault();
        setLoading(true);
        setErrorMessage('');
        setSuccessMessage('');

        try {
            const response = await axios.post(
                'http://localhost:8080/v1/auth/regenerate-otp',
                null,
                { params: { email, otp } }
            );
            setSuccessMessage(response.data.message || 'OTP verified successfully.');
            setIsNewPasswordFieldVisible(true); // Unlock new password field if OTP is verified
        } catch (error) {
            console.error('Error verifying OTP:', error);
            setErrorMessage(error.response?.data?.message || 'OTP verification failed. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    // Step 3: Set New Password
    const handleSetNewPassword = async (event) => {
        event.preventDefault();
        setLoading(true);
        setErrorMessage('');
        setSuccessMessage('');

        try {
            const response = await axios.post(
                'http://localhost:8080/v1/auth/reset',
                null,
                { params: { email, otp, newPassword } }
            );
            setSuccessMessage(response.data.message || 'Password reset successfully.');
            window.location.href = "/signin";
        } catch (error) {
            console.error('Error setting new password:', error);
            setErrorMessage(error.response?.data?.message || 'Failed to reset password. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex min-h-screen items-center justify-center bg-gray-100">
            <div className="w-full max-w-md bg-white shadow-lg rounded-lg p-8">
                <h2 className="text-2xl font-bold text-center mb-6">Reset Password</h2>

                {/* Step 1: Email Input */}
                <form onSubmit={handleSendEmail}>
                    <div className="mb-4">
                        <label className="block text-gray-700">Email Address</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                            className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-400"
                        />
                    </div>
                    <button
                        type="submit"
                        className={`w-full mt-6 px-4 py-2 text-white bg-teal-500 rounded-lg hover:bg-teal-600 ${loading ? 'opacity-50 cursor-not-allowed' : ''}`}
                        disabled={loading}
                    >
                        {loading ? 'Sending...' : 'Send Reset OTP'}
                    </button>
                </form>

                {/* Display OTP Input if email was sent successfully */}
                {isOtpFieldVisible && (
                    <form onSubmit={handleVerifyOtp} className="mt-4">
                        <div className="mb-4">
                            <label className="block text-gray-700">Enter OTP</label>
                            <input
                                type="text"
                                value={otp}
                                onChange={(e) => setOtp(e.target.value)}
                                required
                                className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-400"
                            />
                        </div>
                        <button
                            type="submit"
                            className={`w-full mt-4 px-4 py-2 text-white bg-teal-500 rounded-lg hover:bg-teal-600 ${loading ? 'opacity-50 cursor-not-allowed' : ''}`}
                            disabled={loading}
                        >
                            {loading ? 'Verifying...' : 'Verify OTP'}
                        </button>
                    </form>
                )}

                {/* Display New Password Input if OTP is verified */}
                {isNewPasswordFieldVisible && (
                    <form onSubmit={handleSetNewPassword} className="mt-4">
                        <div className="mb-4">
                            <label className="block text-gray-700">New Password</label>
                            <input
                                type="password"
                                value={newPassword}
                                onChange={(e) => setNewPassword(e.target.value)}
                                required
                                className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-400"
                            />
                        </div>
                        <button
                            type="submit"
                            className={`w-full mt-4 px-4 py-2 text-white bg-teal-500 rounded-lg hover:bg-teal-600 ${loading ? 'opacity-50 cursor-not-allowed' : ''}`}
                            disabled={loading}
                        >
                            {loading ? 'Resetting...' : 'Set New Password'}
                        </button>
                    </form>
                )}

                {/* Display success or error messages */}
                {errorMessage && <p className="mt-4 text-red-600 text-center">{errorMessage}</p>}
                {successMessage && <p className="mt-4 text-green-600 text-center">{successMessage}</p>}
            </div>
        </div>
    );
};

export default ResetEmail;