import React from 'react';
import Section_One from '../Components/Section-1/Section_One';
import WhatIsIGELP from '../Components/InfoSection/WhatIsIGELP'; 
import  Success from '../Components/Success/Success'; 
import TestimonialSection from '../Components/TestimonialSection/TestimonialSection'; 
import PhysicalClassroom from '../Components/PhysicalClassroom/PhysicalClassroom';
import CloudSoftwareSection from '../Components/Section-2/Section_One';
const Layout = () => {
  return (
    <>
      <div>
      <Success/>
      <CloudSoftwareSection/>
      <WhatIsIGELP />
        <PhysicalClassroom/>
        {/* <Section_One /> */}
        <TestimonialSection/>
      </div>
    </>
  );
};

export default Layout;
