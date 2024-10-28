import React from 'react';
import imgage from "../../assets/student1.jpg";  // Replace this URL with the actual image URL
import imgage1 from "../../assets/instructore.jpg";

const WhatIsIGELP = () => {
  return (
    <div className="bg-white text-center py-16">
  <h2 className="text-4xl font-bold text-blue-600 mb-6">What is IGELP?</h2>
  <p className="text-gray-600 text-lg mb-12 mx-auto max-w-3xl px-4">
    <span className="font-bold text-black">IGELP</span> is a platform that allows educators to create online classes where they can store the course materials online, manage assignments, quizzes, and exams, monitor due dates, grade results, and provide students with feedback all in one place.
  </p>

  <div className="flex flex-col md:flex-row justify-center gap-10 px-4">
    <div className="relative w-full md:w-96 h-56 bg-gray-200 rounded-lg shadow-lg overflow-hidden">
      <img
        src={imgage1} // Replace with actual image URL
        alt="For Instructors"
        className="w-full h-full object-cover"
      />
      <div className="absolute inset-0 bg-black bg-opacity-50 flex flex-col items-center justify-center">
        <h3 className="text-white text-xl font-bold">FOR INSTRUCTORS</h3>
        <button className="bg-blue-500 text-white px-4 py-2 rounded-full mt-4 hover:bg-blue-600 transition">
          Start Today
        </button>
      </div>
    </div>

    <div className="relative w-full md:w-96 h-56 bg-gray-200 rounded-lg shadow-lg overflow-hidden">
      <img
        src={imgage} // Replace with actual image URL
        alt="For Students"
        className="w-full h-full object-cover"
      />
      <div className="absolute inset-0 bg-black bg-opacity-50 flex flex-col items-center justify-center">
        <h3 className="text-white text-xl font-bold">FOR STUDENTS</h3>
        <button className="bg-blue-500 text-white px-4 py-2 rounded-full mt-4 hover:bg-blue-600 transition">
          Join Us
        </button>
      </div>
    </div>
  </div>
</div>

  );
};

export default WhatIsIGELP;
