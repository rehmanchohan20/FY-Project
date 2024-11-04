// src/pages/StudentPanel.js
import React from 'react';

const StudentPanel = () => {
    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
            <h1 className="text-3xl font-bold text-gray-800 mb-6">Student Panel</h1>
            <p className="text-gray-600">Welcome, Student! Here you can access your courses and resources.</p>
            {/* Add more student-specific functionality here */}
        </div>
    );
};

export default StudentPanel;
