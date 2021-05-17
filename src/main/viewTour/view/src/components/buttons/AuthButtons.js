import React from 'react';
import'../../assets/main.css'
export function RegisterButton(props) {
    return (
        <div>
        <button className="lg:p-4 py-3 px-0 block border-b-2 border-transparent hover:hover-indigo-400" onClick={props.onClick}>
        Register
        </button>
        </div>
    );
}

export function LoginButton(props) {
    return (
        <button className="lg:p-4 py-3 px-0 block border-b-2 border-transparent hover:hover-indigo-400" onClick={props.onClick}>
        Login
        </button>
    );
}

export function LogoutButton(props) {
    return (
        <button className="lg:p-4 py-3 px-0 block border-b-2 border-transparent hover:hover-indigo-400" onClick={props.onClick}>
        Logout
        </button>
    );
}