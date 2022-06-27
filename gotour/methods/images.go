package main

import (
	"image"
	"image/color"

	"golang.org/x/tour/pic"
)

type Image struct {
	gen    func(int, int) uint8
	bounds image.Rectangle
	model  color.Model
}

func NewImage(
	gen func(int, int) uint8,
	origin image.Point,
	w, h int,
	model color.Model) Image {

	max := image.Point{origin.X + w, origin.Y + h}
	bounds := image.Rectangle{origin, max}
	return Image{gen, bounds, model}
}

func ZeroOriginImage(
	gen func(int, int) uint8,
	w, h int,
	model color.Model) Image {

	return NewImage(gen, image.ZP, w, h, model)
}

// ColorModel returns the Image's color model.
func (self Image) ColorModel() color.Model {
	return self.model
}

// Bounds returns the domain for which At can return non-zero color.
// The bounds do not necessarily contain the point (0, 0).
func (self Image) Bounds() image.Rectangle {
	return self.bounds
}

// At returns the color of the pixel at (x, y).
// At(Bounds().Min.X, Bounds().Min.Y) returns the upper-left pixel of the grid.
// At(Bounds().Max.X-1, Bounds().Max.Y-1) returns the lower-right one.
func (self Image) At(x, y int) color.Color {
	ptColor := self.gen(x, y)
	return self.model.Convert(color.Gray{ptColor})
}

func main() {
	w := 256
	h := 256
	gen := func(x, y int) uint8 {
		return uint8((x + y) / 2)
	}
	model := color.ModelFunc(func(c color.Color) color.Color {
		r, g, _, _ := c.RGBA()
		return color.RGBA{uint8(r), uint8(g), 255, 255}
	})
	m := ZeroOriginImage(gen, w, h, model)
	pic.ShowImage(m)
}
