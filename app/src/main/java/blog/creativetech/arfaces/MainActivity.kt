package blog.creativetech.arfaces

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import blog.creativetech.arfaces.arface.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AugmentedFaceListener {

    private var faceMasks = ArrayList<FaceMask>()
    private var currentMaskIndex : Int = 0
    private var updateMask : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (face_view as AugmentedFaceFragment).setAugmentedFaceListener(this)
        initMasks()
        change_button.setOnClickListener { nextMask() }
    }

    override fun onFaceAdded(face: AugmentedFaceNode) {
        changeMask(face)
    }

    override fun onFaceUpdate(face: AugmentedFaceNode) {
        if (updateMask) {
            changeMask(face)
            updateMask = false
        }
    }

    private fun initMasks() {
        val noLandmarks = ArrayList<FaceMaskElement>()

        faceMasks.add(FaceMask("models/contour.png", noLandmarks))

        val glassesLandmarks = ArrayList<FaceMaskElement>()

        glassesLandmarks.add(FaceMaskElement(
            AugmentedFaceNode.Companion.FaceLandmark.EYE_CENTER,
            "models/untitled.obj",
            "models/glasses.png"))

        faceMasks.add(FaceMask("models/contour.png", glassesLandmarks))
    }

    private fun nextMask() {
        currentMaskIndex ++
        if (currentMaskIndex == faceMasks.size) {
            currentMaskIndex = 0
        }
        updateMask = true
    }

    private fun changeMask(face: AugmentedFaceNode) {
        val currentMask = faceMasks[currentMaskIndex]

        currentMask.faceTexture?.let {
            face.setFaceMeshTexture(it)
        }

        face.clearLandmarks()
        currentMask.landmarkMap?.let { landmarks ->
            for (landmark in landmarks) {
                face.setRegionModel(landmark)
            }
        }
    }
}