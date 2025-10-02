import '.Layout.css'
import { useState } from 'react';
import { Link } from 'react-router-dom';

// TODO: ( CREATE A CONNECTION CHECKER )
export const Navbar: React.FC = () => {
    const [isOpen, setIsOpen] = useState(false);

    const toggleView = () => {
        setIsOpen(!isOpen);
    };

    return (
        
    );
};

export const Footer: React.FC = () => {
    const currentYear = new Date().getFullYear();

    return (
        <footer className='footer'>
            <div className='footer-content'>
                <p className='copyright'>
                    &copy; {currentYear} Jaylen Holloway. All rights reserved.
                </p>
            </div>
        </footer>
    );
};
