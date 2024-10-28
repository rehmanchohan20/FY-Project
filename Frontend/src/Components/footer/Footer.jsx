import React from 'react';

const Footer = () => {
  return (
    <footer className="bg-slate-900 py-8 text-white">
      <div className="container mx-auto px-4">
        {/* Logo and Tagline */}
        <div className="flex flex-col md:flex-row justify-center  items-center mb-6">
          <div className="flex space-x-4">
            {/* Logo */}
            <div className="text-2xl font-bold"> 
              <span className="text-teal-400">IGELP</span>
            </div>
            {/* Tagline */}
            <p className="text-lg font-light">Where Success Begins</p>
          </div>
        </div>

        {/* Newsletter Section */}
        <div className="flex justify-center mb-6">
          <div className="text-center">
            <p className="mb-2">Subscribe to get our Newsletter</p>
            <div className="flex justify-center">
              <input 
                type="email" 
                placeholder="Your Email" 
                className="px-4 py-2 rounded-full w-64 text-black"
              />
              <button className="bg-teal-400 text-white px-4 py-2 rounded-full ml-2 hover:bg-teal-500">
                Subscribe
              </button>
            </div>
          </div>
        </div>

        {/* Footer Links */}
        <div className="flex justify-center space-x-6 text-sm mb-4">
          <a href="#" className="hover:underline">Careers</a>
          <a href="#" className="hover:underline">Privacy Policy</a>
          <a href="#" className="hover:underline">Terms & Conditions</a>
        </div>

        {/* Copyright Section */}
        <div className="text-center text-sm">
          © 2024  IGELP
        </div>
      </div>
    </footer>
  );
};

export default Footer;
