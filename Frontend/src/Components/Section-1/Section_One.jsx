// eslint-disable-next-line no-unused-vars
import React from 'react'
import imgage2 from "../../assets/student1.jpg";  // Replace this URL with the actual image URL

const Section_One = () => {
  return (
    <>
    <div className="bg-white py-16 flex flex-col md:flex-row items-center justify-center px-4">
  <div className="md:w-1/2 text-center md:text-left mb-8 md:mb-0 md:pr-8">
    <h3 className="text-2xl md:text-4xl font-bold text-blue-600">
      Everything you can do in a physical classroom, <span className="text-teal-500">you can do with IGELP</span>
    </h3>
    <p className="text-gray-600 text-lg mt-4">
      IGELP`s software helps in online learning management, scheduling, and payments, all in one secure cloud-based system.
    </p>
    <a href="#" className="inline-block mt-6 text-blue-500 hover:text-blue-700 font-medium">
      Learn more
    </a>
  </div>

  <div className="md:w-1/2 relative">
    <img
      src={imgage2} // Replace with the actual image URL
      alt="Student Learning"
      className="rounded-lg shadow-lg object-cover w-full h-full"
    />
    <div className="absolute inset-0 flex items-center justify-center">
      <button className="bg-white p-4 rounded-full shadow-lg">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          strokeWidth="2"
          stroke="currentColor"
          className="w-8 h-8 text-teal-500"
        >
          <path strokeLinecap="round" strokeLinejoin="round" d="M14.752 11.168l-5.197 3.482c-.513.344-1.179-.068-1.179-.648V9.998c0-.58.666-.992 1.179-.648l5.197 3.482c.487.326.487 1.054 0 1.38z" />
        </svg>
      </button>
    </div>
  </div>
</div>

    </>
  )
}

export default Section_One

