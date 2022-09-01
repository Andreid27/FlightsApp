import DoneIcon from '@mui/icons-material/Done';
import { Paragraph } from 'app/components/Typography';
import { getUserFlights, selectReservations } from 'app/redux/reducers/FlightSlice';
import {React, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';


function SelectButton (props) {
    const dispatch = useDispatch();

  
    useEffect(()=>{
      dispatch(getUserFlights())
    },[])
    const selectedReservation = useSelector(({flightApp}) => flightApp.SelectedReservation);
    console.log(selectedReservation);
    console.log(props);

    function checkSelected(){
      let selected = false;
      if(selectedReservation != null){        
        if(props.id == selectedReservation.id)
         {selected = true}
      }
      return selected;
    }

    return  checkSelected() ? (
      <CheckCircleOutlineIcon/>
    ) :( <DoneIcon />)
    }


    export default SelectButton;