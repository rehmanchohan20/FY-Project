// src/pages/TeacherPanel.js
import React from 'react';

const TeacherPanel = () => {
    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
            <h1 className="text-3xl font-bold text-gray-800 mb-6">Teacher Panel</h1>
            <p className="text-gray-600">Welcome, Teacher! Here you can manage your courses and students.</p>
            {/* Add more teacher-specific functionality here */}
        </div>
    );
};

export default TeacherPanel;
