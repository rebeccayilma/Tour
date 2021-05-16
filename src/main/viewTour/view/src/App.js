import React, { useState, useEffect } from 'react';
import { Header } from './components/Header';
import { Landing } from './components/Landing';
import { Place } from './components/Place';
import { Activity } from './components/Activity';
import { RatingList, Rating } from './components/Ratings';
import { LoginForm } from './components/LoginForm';
import { COOKIES_URL, REGISTER_URL, LOGIN_URL, PLACE_URL } from './http-utils';
import { useCookies } from 'react-cookie';
import axios from 'axios';


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
  const [place, setPlace] = useState({});
  const [activity, setActivity] = useState({});
  const [cookies, setCookie, removeCookie] = useCookies(['cookie-name']);

  const showLoginForm = () => {
    setPage("loginForm");
  }

  const showRegisterForm = () => {
    setPage("registerForm");
  }

  /* Logout */
  const logout = () => {
    setLoggedIn(false);
    removeCookie('JWT', { path: COOKIES_URL }); // TODO: check correctness
    setRoles(getUserRoles(false));
    setPage('landing');
  }

  /* Login */
  const login = (username, password) => {
    const loginParams = {
      "username": username,
      "password": password
    }
    
    axios.post(LOGIN_URL, loginParams).then(res => {
      setLoggedIn(true);
      setCookie('JWT', res.headers.authorization, { path: COOKIES_URL });
      setRoles(getUserRoles(true));
      setPage('landing');
    }).catch(err => {
      console.log("Cannot login");
      console.log(err);
      logout();
    });
    
  }

  /* Register */
  const register = (username, password) => {
    const registerParams = {
      "username": username,
      "password": password,
      "role": "CONTRIBUTOR"
    }
    axios.post(REGISTER_URL, registerParams).then(res => {
      // Register OK
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

  const selectPlace = (place) => {
    setPlace(place);
    setPage('place');
  }

  const addPlace = () => {
    if (roles.some(role => role === 'ADMIN')) {
      setPage('newPlace');
    }
    // TODO
  }

  const seeProposedActivities = () => {
    if (roles.some(role => role === 'ADMIN')) {
      setPage('inactiveActivities');
    }
    // TODO
  }

  const clickActivity = (activity) => {
    setActivity(activity);
    setPage('activity');
  }

  const clickSeeRatings = (activityId) => {
    if (roles.some(role => role === 'CONTRIBUTOR')) {
      setPage('ratings');
    }
    // TODO
  }

  const rate = () => {
    if (roles.some(role => role === 'CONTRIBUTOR')) {
      setPage('rateActivity');
    }
    // TODO
  }

  const saveRating = (activityId) => {
    // TODO: submit rating to backend
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
    'rate': rate
  }

  const ratingFunctions = {
    'saveRating': saveRating
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

        {page === 'loginForm' && (<LoginForm submit={login} />)}

        {page === 'registerForm' && (<LoginForm submit={register} />)}
        
        {page === 'place' && (<Place func={placeFunctions} place={place}/>)}

        {page === 'activity' && (<Activity func={activityFunctions} activity={activity}/>)}

        {page === 'ratings' && (<RatingList func={ratingFunctions} activity={activity}/>)}

        {page === 'rateActivity' && (<Rating activity={activity}/>)}
      </div>
    </div>
  );
}

export default App;
