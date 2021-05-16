import React, { useState } from 'react';
import { Header } from './components/Header';
import { Landing } from './components/Landing';
import { Place } from './components/Place';
import { Activity } from './components/Activity';
import { InactiveActivities } from './components/InactiveActivities';
import { RatingList } from './components/Ratings';
import { LoginForm } from './components/forms/AuthForm';
import { NewActivityForm } from './components/forms/NewActivityForm';
import { NewPlaceForm } from './components/forms/NewPlaceForm';
import { NewRatingForm } from './components/forms/NewRatingForm';
import { COOKIES_URL, REGISTER_URL, LOGIN_URL, APPROVE_URL, ACTIVITY_URL } from './http-utils';
import { useCookies } from 'react-cookie';
import axios from 'axios';


const getUserRoles = (isLoggedIn) => {
  return isLoggedIn ? () => {
    // TODO: connection with backend + get roles
    // TODO: remove default values ([])
    return ["CONTRIBUTOR", "ADMIN"];
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

  const showAddPlaceForm = () => {
    if (roles.some(role => role === 'ADMIN')) {
      setPage('newPlaceForm');
    }
  }

  const addPlace = (name, latitude, longitude, description) => {
    if (roles.some(role => role === 'ADMIN')) {
      setPage('newPlace');
      // TODO: post place
    }
  }

  const seeProposedActivities = () => {
    if (roles.some(role => role === 'ADMIN')) {
      setPage('inactiveActivities');
    }
  }

  const showAddActivityForm = (place) => {
    if (roles.some(role => role === 'CONTRIBUTOR')) {
      setPlace(place);
      setPage('newActivityForm');
    }
  }

  const addActivity = (info, place) => {
    if (roles.some(role => role === 'CONTRIBUTOR')) {
      // TODO: post activity
      setPage('landing');
    }
  }

  const selectActivity = (activity) => {
    setActivity(activity);
    setPage('activity');
  }

  const clickSeeRatings = (activityId) => {
    if (roles.some(role => role === 'CONTRIBUTOR')) {
      setPage('ratings');
    }
    // TODO
  }

  const approveActivity = (activityId) => {
    if (roles.some(role => role === 'ADMIN')) {
      const config = {
        headers: {
          'Authorization': cookies['JWT']
        }
      }
      axios.patch(APPROVE_URL(activityId), {}, config).then(res => {
        console.log('Activity approved');
        axios.get(ACTIVITY_URL + activityId, config).then(res => selectActivity(res.data));
      }).catch(err => {
        console.log("Cannot approve activity");
        console.log(err);
      });
    }
  }

  const showAddRatingForm = (activity) => {
    if (roles.some(role => role === 'CONTRIBUTOR')) {
      setActivity(activity);
      setPage('newRatingForm');
    }
  }

  const addRating = (score, activity) => {
    if (roles.some(role => role === 'CONTRIBUTOR')) {
      // TODO: post rating
      setPage('landing');
    }
  }

  const headerFunctions = {
    'login': showLoginForm,
    'logout': logout,
    'register': showRegisterForm,
    'home': landing
  }

  const landingFunctions = {
    'selectPlace': selectPlace,
    'addPlace': showAddPlaceForm,
    'seeProposedActivities': seeProposedActivities
  }

  const placeFunctions = {
    'selectActivity': selectActivity,
    'addActivity': showAddActivityForm
  }

  const activityFunctions = {
    'seeRatings': clickSeeRatings,
    'rate': showAddRatingForm
  }

  const inactiveActivityFunctions = {
    'approve': approveActivity
  }

  const ratingFunctions = {
    'saveRating': addRating
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
        {page === 'loginForm' && (<LoginForm submit={login} />)}

        {page === 'registerForm' && (<LoginForm submit={register} />)}

        {page === 'newPlaceForm' && (<NewPlaceForm submit={addPlace} />)}

        {page === 'newActivityForm' && (<NewActivityForm submit={addActivity} place={place} />)}

        {page === 'landing' && (<Landing roles={roles} func={landingFunctions} />)}
        
        {page === 'place' && (<Place func={placeFunctions} place={place}/>)}

        {page === 'activity' && (<Activity func={activityFunctions} activity={activity}/>)}

        {page === 'inactiveActivities' && (<InactiveActivities func={inactiveActivityFunctions}/>)}

        {page === 'ratings' && (<RatingList func={ratingFunctions} activity={activity}/>)}

        {page === 'newRatingForm' && (<NewRatingForm submit={addRating} activity={activity} />)}

      </div>
    </div>
  );
}

export default App;
