"use client";

import Link from "next/link";
import Image from "next/image";
// import {
//     Mail,
//     MapPin,
//     Phone
// } from "lucide-react";

import { 
    FaFacebook, 
    FaInstagram, 
    FaTwitter, 
    FaYoutube } from 'react-icons/fa';

export default function Footer() {
    return (
        <footer className="bg-inherit border-t border-gray-200 dark:border-gray-800 text-sm text-gray-600 dark:text-gray-400">
            {/* Trust badges section (optional) */}
            {/*
      <div className="border-b border-gray-200 dark:border-gray-800">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 py-6">
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-6 justify-items-center text-center sm:text-left">
            <div className="flex items-center gap-3">
              <CreditCard className="h-5 w-5 text-green-600 dark:text-green-400" />
              <span>Secure Payment</span>
            </div>
            <div className="flex items-center gap-3">
              <Calendar className="h-5 w-5 text-green-600 dark:text-green-400" />
              <span>30-Day Returns</span>
            </div>
            <div className="flex items-center gap-3">
              <ShieldCheck className="h-5 w-5 text-green-600 dark:text-green-400" />
              <span>2-Year Warranty</span>
            </div>
          </div>
        </div>
      </div>
      */}

            {/* Main footer content */}
            <div className="max-w-7xl mx-auto px-4 sm:px-6 py-10 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-10 text-center sm:text-left">
                {/* About section */}
                <div>
                    <div className="mb-6 flex justify-center sm:justify-start">
                        <Link href="/" className="inline-block">
                            <Image
                                src="/brightside.png"
                                alt="BrightSide Stores Logo"
                                width={100}
                                height={100}
                            />
                        </Link>
                    </div>
                    <p className="mb-6">
                        BrightSide offers premium products with exceptional service. We&apos;re dedicated to quality, sustainability, and customer satisfaction.
                    </p>
                    <div className="flex justify-center sm:justify-start gap-4">
                        <Link href="/" className="hover:text-green-600 dark:hover:text-green-700 transition-colors">
                            <FaFacebook className="h-5 w-5" />
                            <span className="sr-only">Facebook</span>
                        </Link>
                        <Link href="/" className="hover:text-green-600 dark:hover:text-green-700 transition-colors">
                            <FaInstagram className="h-5 w-5" />
                            <span className="sr-only">Instagram</span>
                        </Link>
                        <Link href="/" className="hover:text-green-600 dark:hover:text-green-700 transition-colors">
                            <FaTwitter className="h-5 w-5" />
                            <span className="sr-only">Twitter</span>
                        </Link>
                        <Link href="/" className="hover:text-green-600 dark:hover:text-green-700 transition-colors">
                            <FaYoutube className="h-5 w-5" />
                            <span className="sr-only">YouTube</span>
                        </Link>
                    </div>
                </div>

                {/* Quick links */}
                <div>
                    <h3 className="font-medium text-gray-900 dark:text-white text-lg mb-6">Quick Links</h3>
                    <ul className="space-y-4">
                        {[
                            { href: "/", label: "Home" },
                            { href: "/shop", label: "Shop" },
                            { href: "/about", label: "About Us" },
                            { href: "/contact", label: "Contact Us" },
                            { href: "/blog", label: "Blog" },
                        ].map((link) => (
                            <li key={link.href}>
                                <Link
                                    href={link.href}
                                    className="hover:text-green-600 dark:hover:text-green-700 transition-colors"
                                >
                                    {link.label}
                                </Link>
                            </li>
                        ))}
                    </ul>
                </div>

                {/* Info */}
                <div>
                    <h3 className="font-medium text-gray-900 dark:text-white text-lg mb-6">Policy</h3>
                    <ul className="space-y-4">
                        {[
                            { href: "/privacy-policy", label: "Privacy Policy" },
                            { href: "/terms", label: "Terms & Conditions" },
                            { href: "/faq", label: "FAQs" },
                        ].map((link) => (
                            <li key={link.href}>
                                <Link
                                    href={link.href}
                                    className="hover:text-green-600 dark:hover:text-green-700 transition-colors"
                                >
                                    {link.label}
                                </Link>
                            </li>
                        ))}
                    </ul>
                </div>

                {/* Products */}
                <div>
                    <h3 className="font-medium text-gray-900 dark:text-white text-lg mb-6">Our Products</h3>
                    <ul className="space-y-4">
                        {[
                            { href: "/medical-equipments", label: "Medical Equipments" },
                            { href: "/mobility-aids-support", label: "Mobility Aids & Support" },
                            { href: "/hospital-beds", label: "Hospital Beds" },
                            { href: "/health-monitoring", label: "Health Monitoring Devices" },
                            { href: "/medical-supplies", label: "Medical Supplies" },
                        ].map((link) => (
                            <li key={link.href}>
                                <Link
                                    href={link.href}
                                    className="hover:text-green-600 dark:hover:text-green-700 transition-colors"
                                >
                                    {link.label}
                                </Link>
                            </li>
                        ))}
                    </ul>
                </div>

                {/* Contact */}
                <div>
                    {/* <h3 className="font-medium text-gray-900 dark:text-white text-lg mb-6">Contact Us</h3>
                    <ul className="space-y-4">
                        <li className="flex items-start justify-center sm:justify-start gap-3">
                            <MapPin className="h-5 w-5 text-green-600 dark:text-green-400 mt-0.5" />
                            <span>
                                Bishop Road<br />
                                UpperHill, Nairobi
                            </span>
                        </li>
                        <li className="flex items-center justify-center sm:justify-start gap-3">
                            <Phone className="h-5 w-5 text-green-600 dark:text-green-400" />
                            <span>(800) 123-4567</span>
                        </li>
                        <li className="flex items-start justify-center sm:justify-start gap-3 text-center sm:text-left break-words">
                            <Mail className="h-5 w-5 text-green-600 dark:text-green-400 flex-shrink-0 mt-0.5" />
                            <span>support@brightsidestores.co.ke</span>
                        </li>

                    </ul> */}

                    {/* Newsletter */}
                    {/* <div className="mt-6">
                        <h4 className="font-medium text-gray-900 dark:text-white text-sm mb-3">
                            Subscribe to our newsletter
                        </h4>
                        <form className="flex flex-col sm:flex-row gap-2">
                            <input
                                type="email"
                                placeholder="Your email"
                                className="bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-200 border border-gray-300 dark:border-gray-700 rounded-md px-3 py-2 text-sm flex-1 focus:outline-none focus:ring-2 focus:ring-green-500"
                                required
                            />
                            <button
                                type="submit"
                                className="bg-green-600 hover:bg-green-700 dark:bg-green-700 dark:hover:bg-green-600 text-white rounded-md px-4 py-2 text-sm font-medium transition-colors whitespace-nowrap"
                            >
                                Sign Up
                            </button>
                        </form>
                    </div> */}
                </div>
            </div>

            {/* Bottom bar */}
            <div className="border-t border-gray-200 dark:border-gray-800 py-6">
                <div className="max-w-7xl mx-auto px-4 sm:px-6 flex flex-col sm:flex-row justify-between items-center gap-4">
                    <div className="text-center sm:text-left">
                        © {new Date().getFullYear()} BrightSide Stores. All rights reserved.
                    </div>

                    {/* Payment icons (optional) */}
                    {/*
          <div className="flex flex-wrap justify-center items-center gap-4">
            <Image src="/payment/visa.svg" alt="Visa" width={32} height={20} className="h-5 w-auto" />
            <Image src="/payment/mastercard.svg" alt="Mastercard" width={32} height={20} className="h-5 w-auto" />
            <Image src="/payment/amex.svg" alt="American Express" width={32} height={20} className="h-5 w-auto" />
            <Image src="/payment/paypal.svg" alt="PayPal" width={32} height={20} className="h-5 w-auto" />
            <Image src="/payment/apple-pay.svg" alt="Apple Pay" width={32} height={20} className="h-5 w-auto" />
          </div>
          */}
                </div>
            </div>
        </footer>
    );
}