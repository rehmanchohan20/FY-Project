import React, { useState } from 'react';
import { Link } from "react-router-dom";
import CreateCourse from './Teacher Dashboard/CreateCourse.jsx'; // Import CreateCourse component

function TeacherDashboard() {
    const [showCreateCourse, setShowCreateCourse] = useState(false);

    const handleCreateCourseClick = () => {
        setShowCreateCourse(true);
    };

    return (
        <div className="flex min-h-screen bg-gray-100">
            {/* Sidebar */}
            <aside className="w-1/5 bg-teal-700 text-white flex flex-col items-center py-8">
                <h2 className="text-2xl font-bold mb-10">IGELP</h2>
                <nav className="flex flex-col gap-4 text-lg">
                    <a href="#assignments" className="hover:text-teal-300">Assignments</a>
                    <Link to={"/CraeteCourse"} onClick={handleCreateCourseClick} className="hover:text-teal-300">Create Course</Link>
                    <a href="#quizzes" className="hover:text-teal-300">Quizzes</a>
                    <a href="#upload-results" className="hover:text-teal-300">Upload Results</a>
                </nav>
                <a href="#logout" className="mt-auto hover:text-teal-300">Log Out</a>
            </aside>

            {/* Main Content */}
            <main className="flex-1 p-8">
                {/* Header */}
                <header className="flex justify-between items-center mb-8">
                    <h1 className="text-2xl font-semibold">Dashboard</h1>
                    <div className="flex items-center">
                        <img src="path-to-avatar.jpg" alt="User Avatar" className="w-10 h-10 rounded-full mr-4" />
                        <div>
                            <p className="font-medium">Yasir Ali</p>
                            <p className="text-sm text-gray-500">Instructor</p>
                        </div>
                    </div>
                </header>

                {/* Dashboard Cards */}
                <section className="grid grid-cols-4 gap-4 mb-8">
                    <div className="bg-red-100 text-red-500 p-6 rounded-lg text-center">
                        <p className="text-3xl font-bold">$100</p>
                        <p>Total Sales</p>
                    </div>
                    <div className="bg-yellow-100 text-yellow-500 p-6 rounded-lg text-center">
                        <p className="text-3xl font-bold">2</p>
                        <p>Total Courses</p>
                    </div>
                    <div className="bg-green-100 text-green-500 p-6 rounded-lg text-center">
                        <p className="text-3xl font-bold">5</p>
                        <p>Today's Joining</p>
                    </div>
                    <div className="bg-purple-100 text-purple-500 p-6 rounded-lg text-center">
                        <p className="text-3xl font-bold">1</p>
                        <p>Pending Courses</p>
                    </div>
                </section>

                {/* Additional Sections */}
                <section className="grid grid-cols-2 gap-8">
                    <div className="bg-white p-6 rounded-lg shadow">
                        <h2 className="text-lg font-semibold mb-4">Creative Outdoor Ads</h2>
                        <p className="text-gray-600 mb-4">Every large design company...</p>
                        <button className="bg-teal-500 text-white px-4 py-2 rounded">Get Started</button>
                    </div>
                    <div className="bg-white p-6 rounded-lg shadow">
                        <h2 className="text-lg font-semibold mb-4">Lesson</h2>
                        <div className="flex justify-center">
                            {/* Pie Chart Placeholder */}
                            <div className="w-24 h-24 rounded-full border-4 border-teal-500">
                                {/* Insert pie chart library or SVG */}
                            </div>
                        </div>
                    </div>
                </section>

                {/* Student Ranking */}
                <section className="mt-8">
                    <h2 className="text-lg font-semibold mb-4">Student Ranking</h2>
                    <div className="bg-white p-6 rounded-lg shadow">
                        {/* Bar Chart Placeholder */}
                    </div>
                </section>

                {/* Recent Activity */}
                <section className="mt-8">
                    <h2 className="text-lg font-semibold mb-4">Recent Activity</h2>
                    <ul className="bg-white p-6 rounded-lg shadow space-y-4">
                        <li className="flex items-center">
                            <div className="bg-purple-200 text-purple-600 p-2 rounded-full mr-4">
                                <i className="fas fa-upload"></i>
                            </div>
                            <div>
                                <p>Uploaded Quiz</p>
                                <p className="text-sm text-gray-500">04 Jan, 09:20AM</p>
                            </div>
                        </li>
                        {/* Add more list items similarly */}
                    </ul>
                </section>

                {/* Show CreateCourse component when clicked */}
                {showCreateCourse && <CreateCourse />}
            </main>
        </div>
    );
}

export default TeacherDashboard;