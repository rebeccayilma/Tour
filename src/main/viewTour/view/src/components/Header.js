import { Component } from "react";
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
            <div>
                <h1>Welcome back!</h1>
                {/* TODO: check binding */}
                <LogoutButton onClick={this.handleLogout} />
                <HomeButton onClick={this.home} />
            </div> 
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
            <div>
                <h3>Welcome! Login if want to contribute :)</h3>

                <RegisterButton onClick={this.handleRegister} />
                <LoginButton onClick={this.handleLogin} />
                <HomeButton onClick={this.home} />
            </div>
        );
    }  
}
