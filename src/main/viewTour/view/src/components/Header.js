import { Component } from "react";
import '../assets/main.css'


import { RegisterButton, LoginButton, LogoutButton } from './buttons/AuthButtons';
import { HomeButton } from './buttons/CommonButtons';


export function Header(props) {
    const isLoggedIn = props.isLoggedIn;
    if (isLoggedIn) {
        return <MemberHeader 
            handleLogout={props.func.logout}
            home={props.func.home}
        />;
    }
    return <NormalUserHeader
        handleLogin={props.func.login}
        handleRegister={props.func.register}
        home={props.func.home}
    />;
}

class MemberHeader extends Component {
    constructor(props) {
        super(props);

        this.handleLogout = this.props.handleLogout;
        this.handleLogout = this.handleLogout.bind(this);

        this.home = this.props.home;
        this.home = this.home.bind(this);
    }

    render () {
        return(
            <body className="antialiased bg-gray-200">
            <div className="lg:px-16 px-6 bg-white flex flex-wrap items-center lg:py-0 py-2">
                <div className="flex-1 flex justify-between items-center bg-blue-300">
                    <div className="hidden lg:flex lg:items-center lg:w-auto w-full" id ="menu">
                        <nav>
                            <ul className="lg:flex items-center justify-between text-base text-gray-700 pt-4 lg:pt-0">
                                <li>
                                    <h3 className="lg:p-4 py-3 px-0 block border-b-2 border-transparent hover:hover-indigo-400">Welcome!</h3>
                                </li>
                                <li>
                                    <LogoutButton className="lg:p-4 py-3 px-0 block border-b-2 border-transparent hover:hover-indigo-400"  onClick={this.handleLogout} />

                                </li>
                                <li>
                                    <HomeButton className="lg:p-4 py-3 px-0 block border-b-2 border-transparent hover:hover-indigo-400"  onClick={this.home} />

                                </li>

                            </ul>

                        </nav>
                    </div>

                </div>

            </div>
            </body>
        );
    }  
}

class NormalUserHeader extends Component {
    constructor(props) {
        super(props);

        this.handleRegister = this.props.handleRegister;
        this.handleRegister = this.handleRegister.bind(this);

        this.handleLogin = this.props.handleLogin;
        this.handleLogin = this.handleLogin.bind(this);

        this.home = this.props.home;
        this.home = this.home.bind(this);
    }

    render () {
        return(
            <body className="antialiased bg-gray-200">
            <div className="lg:px-16 px-6 bg-white flex flex-wrap items-center lg:py-0 py-2">
            <div className="flex-1 flex justify-between items-center bg-blue-300">
        <div className="hidden lg:flex lg:items-center lg:w-auto w-full" id ="menu">
                <nav>
                    <ul className="lg:flex items-center justify-between text-base text-gray-700 pt-4 lg:pt-0">
                        <li>
                            <h3 className="lg:p-4 py-3 px-0 block border-b-2 border-transparent hover:hover-indigo-400">Welcome!</h3>
                        </li>
                        <li>
                            <HomeButton className="lg:p-4 py-3 px-0 block border-b-2 border-transparent hover:hover-indigo-400"  onClick={this.home} />

                        </li>
                        <li>
                            <RegisterButton className="lg:p-4 py-3 px-0 block border-b-2 border-transparent hover:hover-indigo-400"  onClick={this.handleRegister} />

                        </li>
                        <li>
                            <LoginButton className="lg:p-4 py-3 px-0 block border-b-2 border-transparent hover:hover-indigo-400"  onClick={this.handleLogin}  />

                        </li>

                    </ul>

                </nav>
        </div>

            </div>

            </div>
            </body>
        );
    }  
}
