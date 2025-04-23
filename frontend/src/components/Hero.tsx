"use client";

import Image from 'next/image';
import Link from 'next/link';
import React from 'react';

type Category = {
  name: string;
  href: string;
};

const categories: Category[] = [
  { name: 'Medical Equipment', href: '/category/medical-equipment' },
  { name: 'Laboratory Equipment', href: '/category/laboratory-equipment' },
  { name: 'Blood Pressure Monitors', href: '/category/blood-pressure-monitors' },
  { name: 'Digital Thermometer', href: '/category/digital-thermometer' },
  { name: 'Wheelchairs', href: '/category/wheelchairs' },
  { name: 'Suction Machines', href: '/category/suction-machines' },
  { name: 'Walking Frames', href: '/category/walking-frames' },
  { name: 'Crutches', href: '/category/crutches' },
  { name: 'Lamps', href: '/category/lamps' },
  { name: 'Stethoscopes', href: '/category/stethoscopes' },
  { name: 'Centrifuge', href: '/category/centrifuge' },
  { name: 'Home Care Equipment', href: '/category/home-care-equipment' },
  { name: 'Oxygen Concentrators', href: '/category/oxygen-concentrators' },
];

const HeroSection: React.FC = () => {
  return (
    <section className="w-full overflow-hidden bg-gray-100 dark:bg-gray-900 transition-colors">
      <div className="grid grid-cols-1 lg:grid-cols-[250px_1fr]">
        
        {/* Left Sidebar - Categories */}
        <aside className="bg-yellow-400 dark:bg-yellow-300 p-6 text-gray-800 dark:text-black">
          <h3 className="font-bold mb-4 text-xl">Categories</h3>
          <ul className="space-y-2">
            {categories.map((category, index) => (
              <li key={index}>
                <Link
                  href={category.href}
                  className="block hover:text-green-700 dark:hover:text-green-900 transition"
                >
                  {category.name}
                </Link>
              </li>
            ))}
          </ul>
        </aside>

        {/* Hero Banner */}
        <div className="relative flex flex-col-reverse lg:flex-row items-center justify-between bg-green-700 dark:bg-green-800 text-white dark:text-white h-full w-full px-8 py-12 lg:py-0 lg:h-[500px]">
          
          {/* Text Content */}
          <div className="flex-1 flex flex-col justify-center max-w-xl">
            <h1 className="text-4xl md:text-5xl font-extrabold mb-4 leading-tight">
              Wheelchair, <br className="hidden md:block" /> Crutches, Walking frames
            </h1>
            <p className="text-lg md:text-l">and Hospital beds</p>
            <div className="mt-6">
              <p className="text-md">up to</p>
              <h2 className="text-4xl font-bold text-yellow-300 dark:text-yellow-400">5% OFF</h2>
            </div>
            <div className="flex gap-2 mt-8">
              {[...Array(5)].map((_, i) => (
                <span key={i} className="w-3 h-3 bg-white bg-opacity-30 rounded-full"></span>
              ))}
              <span className="w-3 h-3 bg-yellow-400 rounded-full"></span>
            </div>
          </div>

          {/* Image */}
          <div className="flex-1 flex justify-center items-center p-6">
            <Image
              src="/assets/images/hero-banners/hospital-bed.png"
              alt="Portable Hospital Bed"
              width={500}
              height={400}
              className="object-contain max-h-[400px] w-auto"
              priority
            />
          </div>
        </div>
      </div>
    </section>
  );
};

export default HeroSection;