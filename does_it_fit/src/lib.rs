pub use crate::areas_volumes::*;
pub mod areas_volumes;

pub fn area_fit(
	x: usize,
	y: usize,
	objects: areas_volumes::GeometricalShapes,
	times: usize,
	a: usize,
	b: usize,
) -> bool {
    let parentarea = rectangle_area(x , y);
    match objects {
        GeometricalShapes::Square => {
            match square_area(a) * times > parentarea {
                true => false,
                false => true,
            }
        },
        GeometricalShapes::Circle => {
            match circle_area(a) as usize * times  > parentarea {
                true => false,
                false => true,
            }
        },
        GeometricalShapes::Rectangle => {
            match rectangle_area(a, b) * times > parentarea {
                true => false,
                false => true,
            }
        },
        GeometricalShapes::Triangle => {
            match triangle_area(a,b) as usize  * times > parentarea {
                true => false,
                false => true,
            }
        }
    }
}
pub fn volume_fit(
	x: usize,
	y: usize,
	z: usize,
	objects: areas_volumes::GeometricalVolumes,
	times: usize,
	a: usize,
	b: usize,
	c: usize,
) -> bool {
    let parentvolume = parallelepiped_volume( x , y , z );
    match objects {
        GeometricalVolumes::Cube => {
            match cube_volume(a) * times > parentvolume {
                true => false,
                false => true,
            }
        }
        GeometricalVolumes::Sphere => {
            match sphere_volume(a) as usize * times > parentvolume {
                true => false,
                false => true,
            }
        }
        GeometricalVolumes::Cone => {
            match cone_volume(a, b) as usize * times > parentvolume {
                true => false,
                false => true,
            }
        }
        GeometricalVolumes::Pyramid => {
            match triangular_pyramid_volume(a as f64 , b ) as usize * times > parentvolume {
                true => false,
                false => true,
            }
        }
        GeometricalVolumes::Parallelepiped => {
            match parallelepiped_volume(a,b,c) * times > parentvolume {
                true => false,
                false => true,
            }
        }
    }
}
