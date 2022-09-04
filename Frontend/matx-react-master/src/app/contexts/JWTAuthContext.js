import React, { createContext, useEffect, useReducer } from 'react';
import jwtDecode from 'jwt-decode';
import axios from 'axios.js';
import * as apiSpec from '../../fake-db/apiSpec';
import { MatxLoading } from 'app/components';

const initialState = {
  isAuthenticated: false,
  isInitialised: false,
  user: null,
};

const isValidToken = (accessToken) => {
  if (!accessToken) {
    return false;
  }

  // const decodedToken = jwtDecode(accessToken)
  const currentTime = Date.now() / 1000;
  let decodedToken = currentTime;
  decodedToken = decodedToken + 10000;
  return (decodedToken = currentTime);
};

const setSession = (email, password, userId, userName, userRole) => {
  localStorage.setItem('email', email);
  localStorage.setItem('password', password);
  localStorage.setItem('userId', userId);
  localStorage.setItem('userName', userName);
  localStorage.setItem('userRole', userRole);
};

const reducer = (state, action) => {
  switch (action.type) {
    case 'INIT': {
      const { isAuthenticated, user } = action.payload;

      return {
        ...state,
        isAuthenticated,
        isInitialised: true,
        user,
      };
    }
    case 'LOGIN': {
      const user = action.payload;

      return {
        ...state,
        isAuthenticated: true,
        user,
      };
    }
    case 'LOGOUT': {
      return {
        ...state,
        isAuthenticated: false,
        user: null,
      };
    }
    case 'REGISTER': {
      const user = action.payload;

      return {
        ...state,
        isAuthenticated: true,
        user,
      };
    }
    default: {
      return { ...state };
    }
  }
};

const AuthContext = createContext({
  ...initialState,
  method: 'JWT',
  login: () => Promise.resolve(),
  logout: () => {},
  register: () => Promise.resolve(),
});

export const AuthProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  const login = async (email, password) => {
    const response = await axios.post(apiSpec.LOGIN, {
      email,
      password,
    });
    const user = response.data;

    setSession(user.email, user.password, user.userId, user.userName, user.userRole);

    dispatch({
      type: 'LOGIN',
      payload: {
        user,
      },
    });
  };

  const register = async (email, userName, password) => {
    const response = await axios.post(apiSpec.REGISTER, {
      userName,
      email,
      password,
    });

    const user = response.data;
    const accessToken = '1231e213e1e2';

    setSession(user.email, user.password, user.userId, user.userName, user.userRole);

    dispatch({
      type: 'REGISTER',
      payload: {
        user,
      },
    });
  };

  const logout = () => {
    setSession(null);
    dispatch({ type: 'LOGOUT' });
  };

  useEffect(() => {
    (async () => {
      try {
        const email = window.localStorage.getItem('email');
        const password = window.localStorage.getItem('password');
        const userName = window.localStorage.getItem('userName');
        const userId = window.localStorage.getItem('userId');
        const userRole = window.localStorage.getItem('userRole');

        if (email && password && userName && userId) {
          setSession(email, password, userId, userName);
          const response = await axios.post(apiSpec.LOGIN, {
            email,
            password,
          });
          const user = response.data;

          dispatch({
            type: 'INIT',
            payload: {
              isAuthenticated: true,
              user,
            },
          });
        } else {
          dispatch({
            type: 'INIT',
            payload: {
              isAuthenticated: false,
              user: null,
            },
          });
        }
      } catch (err) {
        console.error(err);
        dispatch({
          type: 'INIT',
          payload: {
            isAuthenticated: false,
            user: null,
          },
        });
      }
    })();
  }, []);

  if (!state.isInitialised) {
    return <MatxLoading />;
  }

  return (
    <AuthContext.Provider
      value={{
        ...state,
        method: 'JWT',
        login,
        logout,
        register,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
