import React from "react";
import  img from "../../assets/student1.png";
import Layout from "../../Pages/Layout";

const Hero = () => {
  return (
  <>
    <section className="bg-teal-500 text-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 flex flex-col-reverse sm:flex-row items-center justify-between py-12">
        {/* Left Text Section */}
        <div className="sm:w-1/2">
          <h1 className="text-4xl font-bold">
            <span className="text-orange-400">Studying</span> Online is now <br />
            much easier
          </h1>
          <p className="mt-4 text-lg">
            IGELP is an interesting platform that will teach you <br />in a more
            interactive way.
          </p>
          <button className="mt-6 bg-white text-teal-500 px-6 py-3 rounded-full font-medium hover:bg-gray-100">
            Join Us
          </button>
        </div>

        {/* Right Image Section */}
        <div className="sm:w-1/2 relative flex justify-center">
          <img
            src={img} // Replace with the actual image URL
            alt="Student"
            className="w-auto h-auto rounded-lg"
          />

          {/* Circular Div 1 */}
          <div className="absolute top-8 left-8 bg-white text-teal-500 px-4 py-2 rounded-full shadow-lg">
            <p className="text-sm font-medium">250k Assisted Students</p>
          </div>

          {/* Circular Div 2 */}
          <div className="absolute bottom-0 left-10 transform translate-x-20 bg-white text-teal-500 px-4 py-2 rounded-full shadow-lg">
            <p className="text-sm font-medium">Congratulations! Your admission completed</p>
          </div>

          {/* Circular Div 3 */}
          <div className="absolute top-1/4 right-8  ms:transform -translate-y-1/2   transform translate-y-4  transfer translate-x-16 bg-white text-teal-500 px-4 py-2 rounded-full shadow-lg">
            <p className="text-sm font-medium">Come and join us for Quality education</p>
            <button className="mt-2 bg-pink-500 text-white px-3 py-1 rounded-full text-xs hover:bg-pink-600">
              Join Now
            </button>
          </div>
        </div>
      </div>
    </section>
    <Layout/>
   </>
    
  );
};

export default Hero;
