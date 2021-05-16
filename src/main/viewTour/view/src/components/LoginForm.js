import React, { Component } from 'react';

export class LoginForm extends Component {
  constructor(props) {
    super(props);
    // handle initialization activities
  }
  handleChangeEvents(event) {
    //handle change events
  }
  handleSubmitevents(event) {
    // handle submit events
  }
  handlePasswordChange(event){
    //handle password change events
  }
  render() {
    return (
      <p>El login form</p>
      // <div className=" TestLoginForm ">
      //   <form onSubmit={this.handleSubmitevents}>
      //     {
      //       //handle error condition
      //     }
      //     <label>User Name</label>
      //     <input type="text" data-test="username" value={this.state.username} onChange={this.handleChangeEvents} />
      //     <label>Password</label>
      //     <input type="password" data-test="password" value={this.state.password} onChange={this. handlePasswordChange } />
      //     <input type="submit" value="Log In" data-test="submit" />
      //   </form>
      // </div>
    );
  }
}