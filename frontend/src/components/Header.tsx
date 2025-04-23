"use client";

import Link from "next/link";
import { useState } from "react";
import { Leaf, Menu, X } from "lucide-react";

const navLinks = [
  { href: "/shop", label: "Shop" },
  { href: "/about", label: "About" },
  { href: "/contact", label: "Contact" },
  { href: "/cart", label: "Cart 🛒" },
];

export default function Header() {
  const [menuOpen, setMenuOpen] = useState(false);
  const toggleMenu = () => setMenuOpen((prev) => !prev);

  return (
    <header className="border-b dark:border-gray-800">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 py-4 flex items-center justify-between">
        <Link href="/" className="flex items-center gap-2">
          {/* Leaf Icon with fixed responsive size */}
          <Leaf
            color="#4CAF50"
            size={48} // Keep it larger on desktop
            className="sm:w-12 sm:h-12" // Make it responsive, smaller on mobile screens
          />
          <div className="flex flex-col items-center sm:items-start">
            <span className="text-2xl sm:text-3xl font-bold text-gray-800 dark:text-white">
              EcliptaCare
            </span>
            <span className="text-xs sm:text-sm text-gray-600 dark:text-gray-400 mt-1 sm:mt-0 text-center sm:text-left">
              We&apos;re caring, natural, and here to help you thrive with healthcare products.
            </span>
          </div>
        </Link>

        {/* Desktop Nav */}
        <nav className="hidden md:flex gap-8 items-center text-gray-700 dark:text-gray-300">
          {navLinks.map((link) => (
            <Link
              key={link.href}
              href={link.href}
              className="hover:text-green-600 dark:hover:text-green-400 transition-colors duration-200 font-medium"
            >
              {link.label}
            </Link>
          ))}
        </nav>

        {/* Mobile Toggle */}
        <button
          onClick={toggleMenu}
          className="md:hidden text-gray-700 dark:text-gray-300 focus:outline-none focus-visible:ring-2 focus-visible:ring-green-500 focus-visible:ring-offset-2 dark:focus-visible:ring-offset-gray-800"
          aria-label="Toggle menu"
        >
          {menuOpen ? <X size={24} /> : <Menu size={24} />}
        </button>
      </div>

      {/* Mobile Nav */}
      {menuOpen && (
        <nav className="md:hidden px-6 pb-5 pt-2 space-y-3 bg-white dark:bg-inherit border-t dark:border-gray-800 animate-fadeIn">
          {navLinks.map((link) => (
            <Link
              key={link.href}
              href={link.href}
              className="block py-2 text-gray-700 dark:text-gray-300 hover:text-green-600 dark:hover:text-green-400 transition-colors duration-200 font-medium"
              onClick={() => setMenuOpen(false)}
            >
              {link.label}
            </Link>
          ))}
        </nav>
      )}
    </header>
  );
}
