"use client";

import Link from "next/link";
import Image from "next/image";
import {
    Mail,
    MapPin,
    Phone,
} from "lucide-react";
import {
    FaFacebook,
    FaInstagram,
    FaTwitter,
    FaYoutube
} from "react-icons/fa";

export default function Footer() {
    return (
        <footer className="bg-inherit border-t border-gray-200 dark:border-gray-800 text-sm text-gray-600 dark:text-gray-400">
            {/* Main footer content */}
            <div className="max-w-7xl mx-auto pl-[5px] pr-4 sm:pr-6 py-10 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-10 text-center sm:text-left">

                {/* About section */}
                <div>
                <div className="mb-6 flex justify-center sm:justify-start">
                        <Link href="/" className="inline-block">
                            <Image
                                src="/brightside.png"
                                alt="BrightSide Stores Logo"
                                width={100}
                                height={100}
                                className="rounded"
                            />
                        </Link>
                    </div>
                    <p className="mb-6">
                        BrightSide Stores offers premium products with exceptional service. We're dedicated to quality, sustainability, and customer satisfaction.
                    </p>
                    <div className="flex justify-center sm:justify-start gap-4">
                        <Link href="/" className="hover:text-green-600 dark:hover:text-green-700 transition-colors">
                            <FaFacebook className="h-5 w-5" />
                        </Link>
                        <Link href="/" className="hover:text-green-600 dark:hover:text-green-700 transition-colors">
                            <FaInstagram className="h-5 w-5" />
                        </Link>
                        <Link href="/" className="hover:text-green-600 dark:hover:text-green-700 transition-colors">
                            <FaTwitter className="h-5 w-5" />
                        </Link>
                        <Link href="/" className="hover:text-green-600 dark:hover:text-green-700 transition-colors">
                            <FaYoutube className="h-5 w-5" />
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

                {/* Policy */}
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

                {/* Contact + Newsletter */}
                <div>
                    {/* Contact */}
                    <h3 className="font-medium text-gray-900 dark:text-white text-lg mb-6">Contact Us</h3>
                    <ul className="space-y-4 mb-6">
                        <li className="flex items-start justify-center sm:justify-start gap-3">
                            <MapPin className="h-5 w-5 text-green-600 dark:text-green-400 mt-0.5" />
                            <span>
                                BrightSide Stores<br />
                                Office 101, The Healthcare Hub<br />
                                32–34 Maple Street<br />
                                Bloomsbury, London W1T 6HP<br />
                                United Kingdom
                            </span>
                        </li>
                        <li className="flex items-center justify-center sm:justify-start gap-3">
                            <Phone className="h-5 w-5 text-green-600 dark:text-green-400" />
                            <span>
                                UK: +44 20 7946 0958<br />
                                US: +1 (800) 123-4567
                            </span>
                        </li>
                        <li className="flex items-start justify-center sm:justify-start gap-3 break-words">
                            <Mail className="h-5 w-5 text-green-600 dark:text-green-400 mt-0.5" />
                            <span>support@brightsidestores.com</span>
                        </li>
                    </ul>


                    {/* Newsletter */}
                    <div className="w-full max-w-md">
                        <h4 className="font-medium text-gray-900 dark:text-white text-sm mb-3">
                            Subscribe to our newsletter
                        </h4>
                        <form className="flex flex-col sm:flex-row gap-2">
                            <input
                                type="email"
                                placeholder="Your email"
                                className="w-full bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-200 border border-gray-300 dark:border-gray-700 rounded-md px-3 py-2 text-sm flex-1 focus:outline-none focus:ring-2 focus:ring-green-500"
                                required
                            />
                            <button
                                type="submit"
                                className="w-full sm:w-auto bg-green-600 hover:bg-green-700 dark:bg-green-700 dark:hover:bg-green-600 text-white rounded-md px-4 py-2 text-sm font-medium transition-colors"
                            >
                                Sign Up
                            </button>
                        </form>
                    </div>

                </div>
            </div>

            {/* Bottom bar */}
            <div className="border-t border-gray-200 dark:border-gray-800 py-6">
                <div className="max-w-7xl mx-auto px-4 sm:px-6 flex flex-col sm:flex-row justify-between items-center gap-4">
                    <div className="text-center sm:text-left">
                        © {new Date().getFullYear()} BrightSide Stores. All rights reserved.
                    </div>
                </div>
            </div>
        </footer>
    );
}