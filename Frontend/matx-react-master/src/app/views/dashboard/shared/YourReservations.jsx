import {
  Avatar,
  Box,
  Card,
  Icon,
  IconButton,
  MenuItem,
  Select,
  styled,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  TableSortLabel,
  Typography,
  useTheme,
} from '@mui/material';
import { Paragraph } from 'app/components/Typography';
import { getUserFlights, selectReservations } from 'app/redux/reducers/FlightSlice';
import {React, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import { useState } from 'react';
import { isBefore } from 'date-fns';
import _ from 'lodash';
import Tooltip from '@mui/material/Tooltip';

const CardHeader = styled(Box)(() => ({
  display: 'flex',
  paddingLeft: '24px',
  paddingRight: '24px',
  marginBottom: '12px',
  alignItems: 'center',
  justifyContent: 'space-between',
}));

const Title = styled('span')(() => ({
  fontSize: '1rem',
  fontWeight: '500',
  textTransform: 'capitalize',
}));

const SubTitle = styled('span')(({ theme }) => ({
  fontSize: '0.875rem',
  color: theme.palette.text.secondary,
}));

const ProductTable = styled(Table)(() => ({
  minWidth: 400,
  whiteSpace: 'pre',
  '& small': {
    width: 50,
    height: 15,
    borderRadius: 500,
    boxShadow: '0 0 2px 0 rgba(0, 0, 0, 0.12), 0 2px 2px 0 rgba(0, 0, 0, 0.24)',
  },
  '& td': { borderBottom: 'none' },
  '& td:first-of-type': { paddingLeft: '16px !important' },
}));

const Small = styled('small')(({ bgcolor }) => ({
  width: 50,
  height: 15,
  color: '#fff',
  padding: '2px 8px',
  borderRadius: '4px',
  overflow: 'hidden',
  background: bgcolor,
  boxShadow: '0 0 2px 0 rgba(0, 0, 0, 0.12), 0 2px 2px 0 rgba(0, 0, 0, 0.24)',
}));


const rows = [	{
		id: 'departureTimestamp asc',
		categorie: 'departureTimestamp',
		label: 'Departure Ascending',
		sort: true,
		direction: 'asc'
	},
	{
		id: 'departureTimestamp desc',
		categorie: 'departureTimestamp',
		label: 'Departure Descending',
		sort: true,
		direction: 'desc'
	},

	{
		id: 'Destination asc',
		categorie: 'landingLocation',
		label: 'Destination A-Z',
		sort: true,
		direction: 'asc'
	},
	{
		id: 'Destination desc',
		categorie: 'landingLocation',
		label: 'Destination Z-A',
		sort: true,
		direction: 'desc'
	}
];



function YourReservations (props) {
  const { palette } = useTheme();
  const bgError = palette.success.main;
  const bgPrimary = palette.primary.main;
  const bgSecondary = palette.secondary.main;
  const dispatch = useDispatch();
  const user ={
    email : window.localStorage.getItem('email'),
    password : window.localStorage.getItem('password'),
    userName : window.localStorage.getItem('userName'),
    userId : window.localStorage.getItem('userId')
  };

  useEffect(()=>{
    dispatch(getUserFlights(user))
  },[])
  const reservations = useSelector(selectReservations);
  console.log(reservations);

  function hasDepartured(departure){
      let departureTime = new Date(departure);
      let now = new Date;
     return isBefore(departureTime,now);
  }
  function isFling(flight){
      let departureTime =new Date(flight.departureTimestamp);
      let landingTime = new Date(flight.landingTimestamp);
      let now = new Date;
     return (isBefore(departureTime,now)&&isBefore(now,landingTime));
  }

	const [order, setOrder] = useState({
		direction: 'asc',
		id: null
	});

  const [sortCategory, setSortCategory] = useState('departureTimestamp desc');
	const createSortHandler = property => event => {
		setSortCategory(event.target.value);
		let categorie = 'departureTimestamp';
		let direction = 'desc'
		for (let i = 0; i < rows.length; i++) {
			if (rows[i].id === event.target.value) {
				categorie = rows[i].categorie;
				direction = rows[i].direction;
			}
		}
		property = event.target.value;
		const id = categorie;
		setOrder({
			direction,
			id
		});
	}
  useEffect(()=>{
    console.log(order);
  },[order])


  return (
    <Card elevation={3} sx={{ pt: '20px', mb: 3 }}>
      <CardHeader>
        <Title>Your reservations</Title><div>
        <SubTitle>Sort by:  </SubTitle>
        <Select size="small" 								
                defaultValue={30}
								labelId="demo-simple-select-label"
								id="demo-simple-select"
								value={sortCategory}
								onChange={createSortHandler()}
							>
								{rows.map((row) => (
									<MenuItem key={row.id + ' ' + row.direction} value={row.id}>
										{row.label}
									</MenuItem>
								))}
        </Select></div>
      </CardHeader>

      <Box overflow="auto">
        <ProductTable>
          <TableHead>
            <TableRow>
              <TableCell sx={{ px: 3 }} colSpan={5}>
                Destination
              </TableCell>
              <TableCell sx={{ px: 0 }} colSpan={2}>
                Flight Date
              </TableCell>
              <TableCell sx={{ px: 0 }} colSpan={2}>
                Status
              </TableCell>
              <TableCell sx={{ px: 0 }} colSpan={1}>
                Action
              </TableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {_.orderBy(
							reservations,
							[
								reservation => {
									console.log(reservation);
									// console.log(order);
											return reservation.flight[order.id];
									}
								
							],
							[order.direction])
            .map((reservation, index) => (
              <TableRow key={index} hover>
                <TableCell colSpan={5} align="left" sx={{ px: 0, textTransform: 'capitalize' }}>
                  <Box display="flex" alignItems="center">
                    {/* <Avatar src={product.imgUrl} /> */}
                    <Paragraph sx={{ m: 0, ml: 4 }}>{reservation.flight.departureLocation+" - "+reservation.flight.landingLocation }</Paragraph>
                  </Box>
                </TableCell>
                

                <TableCell align="left" colSpan={2} sx={{ px: 0, textTransform: 'capitalize' }}>
                  <div>
                    <Typography variant="caption" display="div">
                  {(new Date(reservation.flight.departureTimestamp).toLocaleString())}
                  </Typography></div><div>
                  <Typography variant="caption" display="div">
                  {(new Date(reservation.flight.landingTimestamp).toLocaleString())}
                  </Typography></div>

                  
                </TableCell>

                <TableCell sx={{ px: 0 }} align="left" colSpan={2}>
                  {hasDepartured(reservation.flight.departureTimestamp) ? (
                    isFling(reservation.flight)  ? (
                      <Small bgcolor={bgSecondary}>Curently is flying</Small>
                    ) : (
                      <Small bgcolor={bgError}>Landed Successfully</Small>
                    )
                  ) : (
                    <Small bgcolor={bgPrimary}>Not Departured</Small>
                  )}
                </TableCell>

                <TableCell sx={{ px: 0 }} colSpan={1}>
                  <IconButton>
                    <Icon color="primary">edit</Icon>
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </ProductTable>
      </Box>
    </Card>
  );
};


export default YourReservations;
