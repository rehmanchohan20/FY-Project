/* eslint-disable react/no-unescaped-entities */
import React from 'react';
import img from '../../assets/image5.jpg';
const PhysicalClassroom = () => {
  return (
    <div className="bg-white py-12 px-4 sm:px-6 lg:px-8 lg:py-16">
      <div className="max-w-7xl mx-auto lg:grid lg:grid-cols-2 lg:gap-8 lg:items-center">
        {/* Left Column - Text */}
        <div>
          <h2 className="text-3xl font-extrabold text-gray-900 sm:text-4xl">
            Everything you can do in a physical classroom, 
            <span className="text-indigo-600"> you can do with IGELP</span>
          </h2>
          <p className="mt-4 text-lg text-gray-500">
            IGELP's software helps in online learning, managing scheduling, payments, 
            all in one secure cloud-based system.
          </p>
          <a href="#" className="mt-8 inline-flex items-center text-indigo-600 hover:text-indigo-900">
            Learn more
          </a>
        </div>

        {/* Right Column - Image with Play Button */}
        <div className="mt-10 lg:mt-0 relative">
          <img
            className="rounded-lg shadow-lg object-cover"
            src={img} // Replace with the actual image URL
            alt="Student working on a laptop"
          />
          <button className="absolute inset-0 flex items-center justify-center bg-black bg-opacity-50 rounded-lg">
            <svg className="w-12 h-12 text-white" fill="currentColor" viewBox="0 0 20 20" aria-hidden="true">
              <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm-1.879-4.516A.5.5 0 017 12.5v-5a.5.5 0 01.757-.429l5 2.5a.5.5 0 010 .858l-5 2.5a.5.5 0 01-.636-.211z" clipRule="evenodd" />
            </svg>
          </button>
        </div>
      </div>
    </div>
  );
};

export default PhysicalClassroom;
