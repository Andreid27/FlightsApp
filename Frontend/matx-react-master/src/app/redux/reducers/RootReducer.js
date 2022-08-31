import { combineReducers } from 'redux';
import EcommerceReducer from './EcommerceReducer';
import FlightSlice from './FlightSlice';
import NavigationReducer from './NavigationReducer';
import NotificationReducer from './NotificationReducer';

const RootReducer = combineReducers({
  notifications: NotificationReducer,
  navigations: NavigationReducer,
  ecommerce: EcommerceReducer,
  flightApp: FlightSlice,
});

export default RootReducer;
