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
import { 
  COOKIES_URL, REGISTER_URL, LOGIN_URL,
  APPROVE_URL, ACTIVITY_URL, PLACE_URL,
  RATING_URL, DEACTIVATE_URL 
} from './http-utils';
import { useCookies } from 'react-cookie';
import axios from 'axios';
import { getCurrentDate } from './utils';
import {RegisterForm} from "./components/forms/RegisterForm";

function App() {
  const [page, setPage] = useState('landing');
  const [isLoggedIn, setLoggedIn] = useState(false);
  const [roles, setRoles] = useState([]);
  const [place, setPlace] = useState({});
  const [activity, setActivity] = useState({});
  const [cookies, setCookie, removeCookie] = useCookies(['cookie-name']);
  
  const sendJWTConfig = () => {
    return {
      headers: {
        "Authorization": cookies['JWT']
      }
    }
  }

  const showLoginForm = () => {
    setPage("loginForm");
  }

  const showRegisterForm = () => {
    setPage("registerForm");
  }

  /* Logout */
  const logout = () => {
    setLoggedIn(false);
    removeCookie('JWT', { path: COOKIES_URL });
    setRoles([]);
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
      setRoles(() => res.data[0].authorities.map((role, index) => role.authority));
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
      const newPlaceParams = {
        "name": name,
        "latitude": latitude,
        "longitude": longitude,
        "description": description
      }

      axios.post(PLACE_URL + '/', newPlaceParams, sendJWTConfig()).then(res => {
        // Create Place OK
        console.log('success posting place');
        // TODO: show pop-up message or sth saying that the place has been posted
        setPage('landing');
      }).catch(err => {
        console.log("Cannot post place");
        // TODO: show pop-up message or sth saying that there's been an error
        console.log(err);
      });
    }
  }

  const seeProposedActivities = () => {
    if (roles.some(role => role === 'ADMIN')) {
      setPage('inactiveActivities');
    }
  }

  const showAddActivityForm = (place) => {
    if (roles.some(role => role === 'CONTRIBUTOR')) {
      console.log("opening new activity form: ", place)
      setPlace(place);
      setPage('newActivityForm');
    }
  }

  const addActivity = (info, place) => {
    if (roles.some(role => role === 'CONTRIBUTOR')) {      
      const newActivityParams = {
        "info": info,
        "place": {
          "id": place.place_id
        }
      }

      axios.post(ACTIVITY_URL + '/', newActivityParams, sendJWTConfig()).then(res => {
        // Create Activity OK
        console.log('success posting activity');
        // TODO: show pop-up message or sth saying that the activity has been posted
        selectPlace(place);
      }).catch(err => {
        console.log("Cannot post activity");
        // TODO: show pop-up message or sth saying that there's been an error
        console.log(err);
      });
    }
  }

  const selectActivity = (activity) => {
    setActivity(activity);
    setPage('activity');
  }

  const clickSeeRatings = (activity) => {
    setActivity(activity);
    setPage('ratings');
  }

  const approveActivity = (activity) => {
    const activityId = activity.activity_id;
    if (roles.some(role => role === 'ADMIN')) {
      axios.patch(APPROVE_URL(activityId), {}, sendJWTConfig()).then(res => {
        // Approve Activity OK
        console.log('Activity approved');
        axios.get(ACTIVITY_URL + '/' + activityId, sendJWTConfig()).then(res => selectActivity(res.data));
      }).catch(err => {
        console.log("Cannot approve activity");
        console.log(err);
      });
    }
  }

  const deactivateActivity = (activity) => {
    if (roles.some(role => role === 'ADMIN')) {
      const activityId = activity.activity_id;
      axios.patch(DEACTIVATE_URL(activityId), {}, sendJWTConfig()).then(res => {
        // Approve Activity OK
        console.log('Activity deactivated');
        setPage('landing');
      }).catch(err => {
        console.log("Cannot deactivate activity");
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
      const newRatingParams = {
        "date": getCurrentDate('-'),
        "score": score,
        "activity": {
          "id": activity.activity_id
        }
      }

      axios.post(RATING_URL + '/', newRatingParams, sendJWTConfig()).then(res => {
        // Rate Activity OK
        console.log('success rating activity');
        // TODO: show pop-up message or sth saying that the rating has been accepted
        selectActivity(activity);
      }).catch(err => {
        console.log("Cannot rate activity");
        // TODO: show pop-up message or sth saying that there's been an error
        console.log(err);
      });
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
    'rate': showAddRatingForm,
    'deactivate': deactivateActivity
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

        {page === 'registerForm' && (<RegisterForm submit={register} />)}

        {page === 'newPlaceForm' && (<NewPlaceForm submit={addPlace} />)}

        {page === 'newActivityForm' && (<NewActivityForm submit={addActivity} place={place} />)}

        {page === 'landing' && (<Landing roles={roles} func={landingFunctions} />)}
        
        {page === 'place' && (<Place func={placeFunctions} roles={roles} place={place}/>)}

        {page === 'activity' && (<Activity func={activityFunctions} roles={roles} activity={activity}/>)}

        {page === 'inactiveActivities' && (<InactiveActivities func={inactiveActivityFunctions}/>)}

        {page === 'ratings' && (<RatingList func={ratingFunctions} activity={activity}/>)}

        {page === 'newRatingForm' && (<NewRatingForm submit={addRating} activity={activity} />)}

      </div>
    </div>
  );
}

export default App;
