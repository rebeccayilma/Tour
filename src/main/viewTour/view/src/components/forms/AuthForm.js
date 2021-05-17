import React, { useState } from 'react';

export function LoginForm(props) {
  const [username, setUsername] = useState([]);
  const [password, setPassword] = useState([]);
  const submit = props.submit;

  const handleSubmit = (evt) => {
    evt.preventDefault();

    if (!username) {
      // TODO: inform error
      // return this.setState({ error: 'Password is required' });
      return;
    }

    if (!password) {
      // TODO: inform error
      // return this.setState({ error: 'Password is required' });
      return;
    }

    submit(username, password);
  }

  const handleUserChange = (evt) => {
    setUsername(evt.target.value);
  }

  const handlePassChange = (evt) => {
    setPassword(evt.target.value);
  }

  return (
    <div className="Login">
      <form onSubmit={handleSubmit}>
        
        <label>Username</label>
        <input type="text" data-test="username" value={username} onChange={handleUserChange} />

        <label>Password</label>
        <input type="password" data-test="password" value={password} onChange={handlePassChange} />

        <input type="submit" value="Submit" data-test="submit" />
      </form>
    </div>
  );
}
