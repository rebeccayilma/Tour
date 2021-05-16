import React, { useState } from 'react';
import { Header } from './components/Header';
import { Landing } from './components/Landing';
import { Place } from './components/Place';
import { Activity } from './components/Activity';
import { RatingList, Rating } from './components/Ratings';
import { LoginForm } from './components/LoginForm';
import { COOKIES_URL, REGISTER_URL, LOGIN_URL } from './http-utils';
import { useCookies } from 'react-cookie';
import { axios } from 'axios';


const getUserRoles = (isLoggedIn) => {
  return isLoggedIn ? () => {
    // TODO: connection with backend + get roles
    return [];
  } : [];
}

function App() {
  const [page, setPage] = useState('landing');
  const [isLoggedIn, setLoggedIn] = useState(false);
  const [roles, setRoles] = useState(getUserRoles(isLoggedIn));
  const [cookies, setCookie, removeCookie] = useCookies(['cookie-name']);

  const showLoginForm = () => {
    setPage("loginForm");
  }

  const showRegisterForm = () => {
    setPage("registerForm");
  }

  const logout = () => {
    setLoggedIn(false);
    removeCookie('JWT', { path: COOKIES_URL }); // TODO: check correctness
    setRoles(getUserRoles(false));
    setPage('landing');
  }

  const login = (username, password) => {
    const loginParams = {
      "username": username,
      "password": password
    }
    axios.post(LOGIN_URL, loginParams).then(res => {
      console.log(res);
      setLoggedIn(true);

      console.log("Headers " + res.headers);
      setCookie('JWT', res.headers.authorization, { path: COOKIES_URL });
      setRoles(getUserRoles(true));
    }).catch(err => {
      console.log("Cannot login");
      console.log(err);
      logout();
    });
  }

  const register = (username, password) => {
    const registerParams = {
      "username": username,
      "password": password,
      "role": "CONTRIBUTOR"
    }
    axios.post(REGISTER_URL, registerParams).then(res => {
      // Register OK
      console.log(res);
      login(username, password);
    }).catch(err => {
      console.log("Cannot register");
      console.log(err);
      logout();
    });
  }

  const landing = () => {
    setPage('landing');
  }

  const selectPlace = (placeId) => {
    setPage('place');
    // TODO: retrieve all activities of place
  }

  const addPlace = () => {
    setPage('newPlace');
    // TODO
  }

  const seeProposedActivities = () => {
    setPage('inactiveActivities');
    // TODO
  }

  const clickActivity = (placeId, activityId) => {
    setPage('activity');
    // TODO
  }

  const clickSeeRatings = (activityId) => {
    setPage('ratings');
    // TODO
  }

  const clickRate = (activityId) => {
    setPage('rateActivity');
    // TODO
  }

  const headerFunctions = {
    'login': showLoginForm,
    'logout': logout,
    'register': showRegisterForm,
    'home': landing
  }

  const landingFunctions = {
    'selectPlace': selectPlace,
    'addPlace': addPlace,
    'seeProposedActivities': seeProposedActivities
  }

  const placeFunctions = {
    'selectActivity': clickActivity
  }

  const activityFunctions = {
    'seeRatings': clickSeeRatings,
    'rate': clickRate
  }

  const ratingFunctions = {

  }

  // ------------
  // Main Control
  // ------------
  return (
    <div className="App">
      <header className="App-header">
        <Header isLoggedIn={isLoggedIn} func={headerFunctions}/>
      </header>

      <div>
        {page === 'landing' && (<Landing roles={roles} func={landingFunctions} />)}

        {page === 'showLoginForm' && (<LoginForm submit={login} />)}

        {page === 'showRegisterForm' && (<LoginForm submit={register} />)}
        
        {page === 'place' && (<Place func={placeFunctions} />)}

        {page === 'activity' && (<Activity func={activityFunctions} />)}

        {page === 'ratings' && (<RatingList func={ratingFunctions} />)}

        {page === 'rateActivity' && (<Rating />)}
      </div>
    </div>
  );
}

export default App;
